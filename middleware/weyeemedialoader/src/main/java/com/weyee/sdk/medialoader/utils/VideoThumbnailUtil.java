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

package com.weyee.sdk.medialoader.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;

/**
 * Created by Taurus on 2017/5/22.
 */

public class VideoThumbnailUtil {

    /**
     * get video thumb from path
     * @param path
     * @return
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

    /**
     * get thumb from system ThumbnailUtils
     * @param path
     * @param kind such as MINI_KIND、MICRO_KIND、FULL_SCREEN_KIND.
     * @return
     */
    public static Bitmap getVideoThumb(String path, int kind) {
        return ThumbnailUtils.createVideoThumbnail(path, kind);
    }

}
