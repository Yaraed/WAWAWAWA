/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.weyee.sdk.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;
import androidx.annotation.WorkerThread;

import static com.weyee.sdk.audio.AudioConfig.DEFAULT_BUFFER_SIZE;
import static com.weyee.sdk.audio.AudioConfig.DEFAULT_SAMPLE_RATE;

/**
 * 实时播放音频
 * @author wuqi by 2019/2/27.
 */
public final class StreamAudioPlayer {
    private static final String TAG = "StreamAudioPlayer";
    // 解码实时音频
    private AudioTrack mAudioTrack;

    private StreamAudioPlayer() {
        // singleton
    }

    private static final class StreamAudioPlayerHolder {
        private static final StreamAudioPlayer INSTANCE = new StreamAudioPlayer();
    }

    public static StreamAudioPlayer getInstance() {
        return StreamAudioPlayerHolder.INSTANCE;
    }

    public synchronized void init() {
        init(DEFAULT_SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                DEFAULT_BUFFER_SIZE);
    }

    /**
     * AudioFormat.CHANNEL_OUT_MONO
     * AudioFormat.ENCODING_PCM_16BIT
     *
     * @param bufferSize user may want to write data larger than minBufferSize, so they should able
     * to increase it
     */
    public synchronized void init(int sampleRate, int channelConfig, int audioFormat,
                                  int bufferSize) {
        if (mAudioTrack != null) {
            mAudioTrack.release();
            mAudioTrack = null;
        }
        int minBufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);
        mAudioTrack =
                new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, channelConfig, audioFormat,
                        Math.max(minBufferSize, bufferSize), AudioTrack.MODE_STREAM);
        mAudioTrack.play();
    }

    @WorkerThread
    public synchronized boolean play(byte[] data, int size) {
        if (mAudioTrack != null) {
            try {
                int ret = mAudioTrack.write(data, 0, size);
                switch (ret) {
                    case AudioTrack.ERROR_INVALID_OPERATION:
                        Log.w(TAG, "play fail: ERROR_INVALID_OPERATION");
                        return false;
                    case AudioTrack.ERROR_BAD_VALUE:
                        Log.w(TAG, "play fail: ERROR_BAD_VALUE");
                        return false;
                    case AudioManager.ERROR_DEAD_OBJECT:
                        Log.w(TAG, "play fail: ERROR_DEAD_OBJECT");
                        return false;
                    default:
                        return true;
                }
            } catch (IllegalStateException e) {
                Log.w(TAG, "play fail: " + e.getMessage());
                return false;
            }
        }
        Log.w(TAG, "play fail: null mAudioTrack");
        return false;
    }

    public synchronized void release() {
        if (mAudioTrack != null) {
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }
}
