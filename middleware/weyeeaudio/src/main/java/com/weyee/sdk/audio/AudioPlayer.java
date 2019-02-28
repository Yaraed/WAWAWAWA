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

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 音频播放
 *
 * @author wuqi by 2019/2/27.
 */
public final class AudioPlayer {
    private static final String TAG = "RxAudioPlayer";

    private MediaPlayer mPlayer;

    private AudioPlayer() {
        // singleton
    }

    public static AudioPlayer getInstance() {
        return AudioPlayerHolder.INSTANCE;
    }

    private MediaPlayer create(final AudioConfig config) throws IOException {
        stopPlay();

        MediaPlayer player;
        switch (config.mType) {
            case AudioConfig.TYPE_URI:
                Log.d(TAG, "MediaPlayer to start play uri: " + config.mResource);
                player = new MediaPlayer();
                player.setDataSource(config.mContext, (Uri) config.mResource);
                return player;
            case AudioConfig.TYPE_FILE:
                Log.d(TAG, "MediaPlayer to start play file: " + ((File) config.mResource).getName());
                player = new MediaPlayer();
                player.setDataSource(((File) config.mResource).getAbsolutePath());
                return player;
            case AudioConfig.TYPE_RES:
                Log.d(TAG, "MediaPlayer to start play: " + config.mResource);
                player = MediaPlayer.create(config.mContext, (int) config.mResource);
                return player;
            case AudioConfig.TYPE_URL:
                Log.d(TAG, "MediaPlayer to start play: " + config.mResource);
                player = new MediaPlayer();
                player.setDataSource((String) config.mResource);
                return player;
            default:
                // can't happen, just fix checkstyle
                throw new IllegalArgumentException("Unknown type: " + config.mType);
        }
    }

    /**
     * play audio from local file. should be scheduled in IO thread.
     */
    public Observable<Boolean> play(@NonNull final AudioConfig config) {
        if (!config.isArgumentValid()) {
            return Observable.error(new IllegalArgumentException(""));
        }

        return Observable.<Boolean>create(emitter -> {
            MediaPlayer player = create(config);
            setMediaPlayerListener(player, emitter);
            player.setVolume(config.mLeftVolume, config.mRightVolume);
            player.setAudioStreamType(config.mStreamType);
            player.setLooping(config.mLooping);
            if (config.needPrepare()) {
                player.prepare();
            }
            player.start();
            mPlayer = player;
            emitter.onNext(true);
        }).doOnError(e -> stopPlay());
    }

    /**
     * prepare audio from local file. should be scheduled in IO thread.
     */
    public Observable<Boolean> prepare(@NonNull final AudioConfig config) {
        if (!config.isArgumentValid() || !config.isLocalSource()) {
            return Observable.error(new IllegalArgumentException(""));
        }

        return Observable.<Boolean>create(emitter -> {
            MediaPlayer player = create(config);
            setMediaPlayerListener(player, emitter);
            player.setVolume(config.mLeftVolume, config.mRightVolume);
            player.setAudioStreamType(config.mStreamType);
            player.setLooping(config.mLooping);
            if (config.needPrepare()) {
                player.prepare();
            }
            mPlayer = player;
            emitter.onNext(true);
        }).doOnError(e -> stopPlay());
    }

    public void pause() {
        mPlayer.pause();
    }

    public void resume() {
        mPlayer.start();
    }

    /**
     * Non reactive API.
     */
    @WorkerThread
    public boolean playNonRxy(@NonNull final AudioConfig config,
                              final MediaPlayer.OnCompletionListener onCompletionListener,
                              final MediaPlayer.OnErrorListener onErrorListener) {
        if (!config.isArgumentValid()) {
            return false;
        }

        try {
            MediaPlayer player = create(config);
            setMediaPlayerListener(player, onCompletionListener, onErrorListener);
            player.setVolume(config.mLeftVolume, config.mRightVolume);
            player.setAudioStreamType(config.mStreamType);
            player.setLooping(config.mLooping);
            if (config.needPrepare()) {
                player.prepare();
            }
            player.start();

            mPlayer = player;
            return true;
        } catch (RuntimeException | IOException e) {
            Log.w(TAG, "startPlay fail, IllegalArgumentException: " + e.getMessage());
            stopPlay();
            return false;
        }
    }

    public synchronized boolean stopPlay() {
        if (mPlayer == null) {
            return false;
        }

        mPlayer.setOnCompletionListener(null);
        mPlayer.setOnErrorListener(null);
        try {
            mPlayer.stop();
            mPlayer.reset();
            mPlayer.release();
        } catch (IllegalStateException e) {
            Log.w(TAG, "stopPlay fail, IllegalStateException: " + e.getMessage());
        }
        mPlayer = null;
        return true;
    }

    public int progress() {
        if (mPlayer != null) {
            return mPlayer.getCurrentPosition() / 1000;
        }
        return 0;
    }

    /**
     * allow further customized manipulation.
     */
    public MediaPlayer getMediaPlayer() {
        return mPlayer;
    }

    private void setMediaPlayerListener(final MediaPlayer player,
                                        final ObservableEmitter<Boolean> emitter) {
        player.setOnCompletionListener(mp -> {
            Log.d(TAG, "OnCompletionListener::onCompletion");

            // could not call stopPlay immediately, otherwise the second sound
            // could not play, thus no complete notification
            // TODO discover why?
            Observable.timer(50, TimeUnit.MILLISECONDS).subscribe(aLong -> {
                stopPlay();
                emitter.onComplete();
            }, emitter::onError);
        });
        player.setOnErrorListener((mp, what, extra) -> {
            Log.d(TAG, "OnErrorListener::onError" + what + ", " + extra);
            emitter.onError(new Throwable("Player error: " + what + ", " + extra));
            stopPlay();
            return true;
        });
    }

    private void setMediaPlayerListener(final MediaPlayer player,
                                        final MediaPlayer.OnCompletionListener onCompletionListener,
                                        final MediaPlayer.OnErrorListener onErrorListener) {
        player.setOnCompletionListener(mp -> {
            Log.d(TAG, "OnCompletionListener::onCompletion");

            // could not call stopPlay immediately, otherwise the second sound
            // could not play, thus no complete notification
            // TODO discover why?
            Observable.timer(50, TimeUnit.MILLISECONDS).subscribe(aLong -> {
                stopPlay();
                onCompletionListener.onCompletion(mp);
            }, throwable -> Log.d(TAG, "OnCompletionListener::onError, " + throwable.getMessage()));
        });
        player.setOnErrorListener((mp, what, extra) -> {
            Log.d(TAG, "OnErrorListener::onError" + what + ", " + extra);
            onErrorListener.onError(mp, what, extra);
            stopPlay();
            return true;
        });
    }

    private static class AudioPlayerHolder {
        private static final AudioPlayer INSTANCE = new AudioPlayer();
    }
}
