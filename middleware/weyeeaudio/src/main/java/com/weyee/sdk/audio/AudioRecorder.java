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

import android.media.MediaRecorder;
import android.util.Log;
import androidx.annotation.IntDef;
import androidx.annotation.WorkerThread;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.weyee.sdk.audio.AudioConfig.DEFAULT_BIT_RATE;
import static com.weyee.sdk.audio.AudioConfig.DEFAULT_SAMPLE_RATE;

/**
 * 音频录制
 * @author wuqi by 2019/2/27.
 */
public final class AudioRecorder {

    public static final int ERROR_SDCARD_ACCESS = 1;
    public static final int ERROR_INTERNAL = 2;
    public static final int ERROR_NOT_PREPARED = 3;

    private static final String TAG = "AudioRecorder";
    private static final int STOP_AUDIO_RECORD_DELAY_MILLIS = 300;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARED = 1;
    private static final int STATE_RECORDING = 2;

    private int mState = STATE_IDLE;
    private OnErrorListener mOnErrorListener;
    private long mSampleStart = 0;       // time at which latest record or play operation started
    private MediaRecorder mRecorder;
    private boolean mStarted = false;

    private AudioRecorder() {
        // singleton
    }

    public static AudioRecorder getInstance() {
        return RxAndroidAudioHolder.INSTANCE;
    }

    public void setOnErrorListener(OnErrorListener listener) {
        mOnErrorListener = listener;
    }

    public synchronized int getMaxAmplitude() {
        if (mState != STATE_RECORDING) {
            return 0;
        }
        return mRecorder.getMaxAmplitude();
    }

    public int progress() {
        if (mState == STATE_RECORDING) {
            return (int) ((System.currentTimeMillis() - mSampleStart) / 1000);
        }
        return 0;
    }

    /**
     * Directly start record, including prepare and start.
     *
     * MediaRecorder.AudioSource.MIC
     * MediaRecorder.OutputFormat.MPEG_4
     * MediaRecorder.AudioEncoder.AAC
     */
    @WorkerThread
    public synchronized boolean startRecord(int audioSource, int outputFormat, int audioEncoder,
                                            int sampleRate, int bitRate, File outputFile) {
        stopRecord();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(audioSource);
        mRecorder.setOutputFormat(outputFormat);
        mRecorder.setAudioSamplingRate(sampleRate);
        mRecorder.setAudioEncodingBitRate(bitRate);
        mRecorder.setAudioEncoder(audioEncoder);
        mRecorder.setOutputFile(outputFile.getAbsolutePath());

        // Handle IOException
        try {
            mRecorder.prepare();
        } catch (IOException | RuntimeException exception) {
            Log.w(TAG, "startRecord fail, prepare fail: " + exception.getMessage());
            setError(ERROR_INTERNAL);
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            return false;
        }
        // Handle RuntimeException if the recording couldn't start
        try {
            mRecorder.start();
            mStarted = true;
        } catch (RuntimeException exception) {
            Log.w(TAG, "startRecord fail, start fail: " + exception.getMessage());
            setError(ERROR_INTERNAL);
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            mStarted = false;
            return false;
        }
        mSampleStart = System.currentTimeMillis();
        mState = STATE_RECORDING;
        return true;
    }

    /**
     * prepare for a new audio record, with default sample rate and bit rate.
     */
    @WorkerThread
    public synchronized boolean prepareRecord(int audioSource, int outputFormat, int audioEncoder,
                                              File outputFile) {
        return prepareRecord(audioSource, outputFormat, audioEncoder, DEFAULT_SAMPLE_RATE,
                DEFAULT_BIT_RATE, outputFile);
    }

    /**
     * prepare for a new audio record.
     */
    @WorkerThread
    public synchronized boolean prepareRecord(int audioSource, int outputFormat, int audioEncoder,
                                              int sampleRate, int bitRate, File outputFile) {
        stopRecord();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(audioSource);
        mRecorder.setOutputFormat(outputFormat);
        mRecorder.setAudioSamplingRate(sampleRate);
        mRecorder.setAudioEncodingBitRate(bitRate);
        mRecorder.setAudioEncoder(audioEncoder);
        mRecorder.setOutputFile(outputFile.getAbsolutePath());

        // Handle IOException
        try {
            mRecorder.prepare();
        } catch (IOException exception) {
            Log.w(TAG, "startRecord fail, prepare fail: " + exception.getMessage());
            setError(ERROR_INTERNAL);
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            return false;
        }
        mState = STATE_PREPARED;
        return true;
    }

    /**
     * After prepared, start record now.
     */
    @WorkerThread
    public synchronized boolean startRecord() {
        if (mRecorder == null || mState != STATE_PREPARED) {
            setError(ERROR_NOT_PREPARED);
            return false;
        }
        // Handle RuntimeException if the recording couldn't start
        try {
            mRecorder.start();
            mStarted = true;
        } catch (RuntimeException exception) {
            Log.w(TAG, "startRecord fail, start fail: " + exception.getMessage());
            setError(ERROR_INTERNAL);
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            mStarted = false;
            return false;
        }
        mSampleStart = System.currentTimeMillis();
        mState = STATE_RECORDING;
        return true;
    }

    /**
     * stop record, and save audio file.
     *
     * @return record audio length in seconds, -1 if not a successful record.
     */
    @WorkerThread
    public synchronized int stopRecord() {
        if (mRecorder == null) {
            mState = STATE_IDLE;
            return -1;
        }

        int length = -1;
        switch (mState) {
            case STATE_RECORDING:
                try {
                    // seems to be a bug in Android's AAC based audio encoders
                    // ref: http://stackoverflow.com/a/24092524/3077508
                    Thread.sleep(STOP_AUDIO_RECORD_DELAY_MILLIS);
                    mRecorder.stop();
                    mStarted = false;
                    length = (int) ((System.currentTimeMillis() - mSampleStart) / 1000);
                } catch (RuntimeException e) {
                    Log.w(TAG, "stopRecord fail, stop fail(no audio data recorded): " +
                            e.getMessage());
                } catch (InterruptedException e) {
                    Log.w(TAG,
                            "stopRecord fail, stop fail(InterruptedException): " + e.getMessage());
                }
                // fall down
            case STATE_PREPARED:
                // fall down
            case STATE_IDLE:
                // fall down
            default:
                try {
                    mRecorder.reset();
                } catch (RuntimeException e) {
                    Log.w(TAG, "stopRecord fail, reset fail " + e.getMessage());
                }
                mRecorder.release();
                mRecorder = null;
                mState = STATE_IDLE;
                break;
        }

        return length;
    }

    private void setError(int error) {
        if (mOnErrorListener != null) {
            mOnErrorListener.onError(error);
        }
    }

    /* returns recorder is started or not */
    public boolean isStarted() {
        return mStarted;
    }

    @IntDef(value = { ERROR_SDCARD_ACCESS, ERROR_INTERNAL, ERROR_NOT_PREPARED })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Error {
    }

    public interface OnErrorListener {
        @WorkerThread
        void onError(@Error int error);
    }

    private static class RxAndroidAudioHolder {
        private static final AudioRecorder INSTANCE = new AudioRecorder();
    }
}
