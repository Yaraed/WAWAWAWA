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

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author wuqi by 2019/2/27.
 */
public class AudioConfig {
    public static final int DEFAULT_SAMPLE_RATE = 44100;
    public static final int DEFAULT_BIT_RATE = 44100;
    public static final int DEFAULT_BUFFER_SIZE = 2048;

    static final int TYPE_FILE = 1;
    static final int TYPE_RES = 2;
    static final int TYPE_URL = 3;
    static final int TYPE_URI = 4;

    /**
     * 文件的类型
     */
    @AudioConfig.Type
    final int mType;

    final Context mContext;

    /**
     * 支持的类型：@RawRes，Uri,File,Url
     */
    final Object mResource;

    /**
     * 流媒体类型
     */
    final int mStreamType;

    final boolean mLooping;

    @FloatRange(from = 0.0F, to = 1.0F)
    final float mLeftVolume;

    @FloatRange(from = 0.0F, to = 1.0F)
    final float mRightVolume;

    private AudioConfig(Builder builder) {
        mType = builder.mType;
        mContext = builder.mContext;
        mStreamType = builder.mStreamType;
        mLooping = builder.mLooping;
        mLeftVolume = builder.mLeftVolume;
        mRightVolume = builder.mRightVolume;
        mResource = builder.mResource;
    }

    public static Builder resource(Context context, Object resource) {
        Builder builder = new Builder();
        builder.mContext = context;
        if (resource instanceof Uri) {
            builder.mResource = resource;
            builder.mType = TYPE_URI;
        } else if (resource instanceof Integer) {
            builder.mResource = resource;
            builder.mType = TYPE_RES;
        } else if (resource instanceof String) {
            builder.mResource = resource;
            builder.mType = TYPE_URL;
        } else if (resource instanceof File) {
            builder.mResource = resource;
            builder.mType = TYPE_FILE;
        }
        return builder;
    }

    boolean isArgumentValid() {
        switch (mType) {
            case TYPE_FILE:
                return mResource instanceof File && ((File) mResource).exists();
            case TYPE_RES:
                return mResource instanceof Integer && ((Integer) mResource) > 0 && mContext != null;
            case TYPE_URL:
                return mResource instanceof String && !TextUtils.isEmpty((String) mResource);
            case TYPE_URI:
                return mResource != null;
            default:
                return false;
        }
    }

    boolean isLocalSource() {
        switch (mType) {
            case AudioConfig.TYPE_FILE:
            case AudioConfig.TYPE_RES:
                return true;
            case AudioConfig.TYPE_URL:
            case AudioConfig.TYPE_URI:
            default:
                return false;
        }
    }

    boolean needPrepare() {
        switch (mType) {
            case AudioConfig.TYPE_FILE:
            case AudioConfig.TYPE_URL:
            case AudioConfig.TYPE_URI:
                return true;
            case AudioConfig.TYPE_RES:
            default:
                return false;
        }
    }

    @IntDef(value = {TYPE_FILE, TYPE_RES, TYPE_URL, TYPE_URI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static class Builder {

        @AudioConfig.Type
        int mType;

        Context mContext;

//        @RawRes
//        int mAudioResource;
//
//        Uri mUri;
//
//        File mAudioFile;
//
//        String mUrl;

        /**
         * 资源文件，暂时支持4中类型：@RawRes，Uri,File,Url
         */
        Object mResource;

        int mStreamType = AudioManager.STREAM_MUSIC;

        boolean mLooping;

        @FloatRange(from = 0.0F, to = 1.0F)
        float mLeftVolume = 1.0F;

        @FloatRange(from = 0.0F, to = 1.0F)
        float mRightVolume = 1.0F;

        /**
         * {@link AudioManager#STREAM_VOICE_CALL} etc.
         */
        public Builder streamType(int streamType) {
            mStreamType = streamType;
            return this;
        }

        public Builder looping(boolean looping) {
            mLooping = looping;
            return this;
        }

        public Builder leftVolume(@FloatRange(from = 0.0F, to = 1.0F) float leftVolume) {
            mLeftVolume = leftVolume;
            return this;
        }

        public Builder rightVolume(@FloatRange(from = 0.0F, to = 1.0F) float rightVolume) {
            mRightVolume = rightVolume;
            return this;
        }

        public AudioConfig build() {
            return new AudioConfig(this);
        }
    }
}
