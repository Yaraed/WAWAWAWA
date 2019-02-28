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
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.weyee.sdk.audio.AudioConfig.DEFAULT_BUFFER_SIZE;
import static com.weyee.sdk.audio.AudioConfig.DEFAULT_SAMPLE_RATE;

/**
 * 实时录制音频，获取的流文件可实时编辑
 * @author wuqi by 2019/2/27.
 */
public final class StreamAudioRecorder {

    private static final String TAG = "StreamAudioRecorder";

    private final AtomicBoolean mIsRecording;
    private ExecutorService mExecutorService;

    private StreamAudioRecorder() {
        // singleton
        mIsRecording = new AtomicBoolean(false);
    }

    public static StreamAudioRecorder getInstance() {
        return StreamAudioRecorderHolder.INSTANCE;
    }

    public synchronized boolean start(@NonNull AudioDataCallback audioDataCallback) {
        return start(DEFAULT_SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, DEFAULT_BUFFER_SIZE, audioDataCallback);
    }

    /**
     * AudioFormat.CHANNEL_IN_MONO
     * AudioFormat.ENCODING_PCM_16BIT
     */
    public synchronized boolean start(int sampleRate, int channelConfig, int audioFormat,
                                      int bufferSize, @NonNull AudioDataCallback audioDataCallback) {
        stop();

        mExecutorService = Executors.newSingleThreadExecutor();
        if (mIsRecording.compareAndSet(false, true)) {
            mExecutorService.execute(
                    new AudioRecordRunnable(sampleRate, channelConfig, audioFormat, bufferSize,
                            audioDataCallback));
            return true;
        }
        return false;
    }

    public synchronized void stop() {
        mIsRecording.compareAndSet(true, false);

        if (mExecutorService != null) {
            mExecutorService.shutdown();
            mExecutorService = null;
        }
    }

    /**
     * Although Android frameworks jni implementation are the same for ENCODING_PCM_16BIT and
     * ENCODING_PCM_8BIT, the Java doc declared that the buffer type should be the corresponding
     * type, so we use different ways.
     */
    public interface AudioDataCallback {
        @WorkerThread
        void onAudioData(byte[] data, int size);

        void onError();
    }

    private static final class StreamAudioRecorderHolder {
        private static final StreamAudioRecorder INSTANCE = new StreamAudioRecorder();
    }

    private class AudioRecordRunnable implements Runnable {

        private final AudioRecord mAudioRecord;
        private final AudioDataCallback mAudioDataCallback;

        private final byte[] mByteBuffer;
        private final short[] mShortBuffer;
        private final int mByteBufferSize;
        private final int mShortBufferSize;
        private final int mAudioFormat;

        AudioRecordRunnable(int sampleRate, int channelConfig, int audioFormat, int byteBufferSize,
                            @NonNull AudioDataCallback audioDataCallback) {
            mAudioFormat = audioFormat;
            int minBufferSize =
                    AudioRecord.getMinBufferSize(sampleRate, channelConfig, mAudioFormat);
            mByteBufferSize = byteBufferSize;
            mShortBufferSize = mByteBufferSize / 2;
            mByteBuffer = new byte[mByteBufferSize];
            mShortBuffer = new short[mShortBufferSize];
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig,
                    audioFormat, Math.max(minBufferSize, byteBufferSize));
            mAudioDataCallback = audioDataCallback;
        }

        @Override
        public void run() {
            if (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                try {
                    mAudioRecord.startRecording();
                } catch (IllegalStateException e) {
                    Log.w(TAG, "startRecording fail: " + e.getMessage());
                    mAudioDataCallback.onError();
                    return;
                }
                while (mIsRecording.get()) {
                    int ret;
                    if (mAudioFormat == AudioFormat.ENCODING_PCM_16BIT) {
                        ret = mAudioRecord.read(mShortBuffer, 0, mShortBufferSize);
                        if (ret > 0) {
                            mAudioDataCallback.onAudioData(
                                    short2byte(mShortBuffer, ret, mByteBuffer), ret * 2);
                        } else {
                            onError(ret);
                            break;
                        }
                    } else {
                        ret = mAudioRecord.read(mByteBuffer, 0, mByteBufferSize);
                        if (ret > 0) {
                            mAudioDataCallback.onAudioData(mByteBuffer, ret);
                        } else {
                            onError(ret);
                            break;
                        }
                    }
                }
            }
            mAudioRecord.release();
        }

        private byte[] short2byte(short[] sData, int size, byte[] bData) {
            if (size > sData.length || size * 2 > bData.length) {
                Log.w(TAG, "short2byte: too long short data array");
            }
            for (int i = 0; i < size; i++) {
                bData[i * 2] = (byte) (sData[i] & 0x00FF);
                bData[(i * 2) + 1] = (byte) (sData[i] >> 8);
            }
            return bData;
        }

        private void onError(int errorCode) {
            if (errorCode == AudioRecord.ERROR_INVALID_OPERATION) {
                Log.w(TAG, "record fail: ERROR_INVALID_OPERATION");
                mAudioDataCallback.onError();
            } else if (errorCode == AudioRecord.ERROR_BAD_VALUE) {
                Log.w(TAG, "record fail: ERROR_BAD_VALUE");
                mAudioDataCallback.onError();
            }
        }
    }
}
