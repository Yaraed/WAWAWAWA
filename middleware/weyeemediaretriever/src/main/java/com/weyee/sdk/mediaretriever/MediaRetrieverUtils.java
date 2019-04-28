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

package com.weyee.sdk.mediaretriever;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.Surface;
import androidx.annotation.Nullable;
import wseemann.media.FFmpegMediaMetadataRetriever;

import java.text.Collator;
import java.util.*;

/**
 * 获取多媒体文件元数据信息
 *
 * @author wuqi by 2019/3/13.
 */
public class MediaRetrieverUtils {

    /**
     * 根据指定标志排序
     */
    private static final Comparator<Metadata> ALPHA_COMPARATOR = new Comparator<Metadata>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(Metadata object1, Metadata object2) {
            return sCollator.compare(object1.getKey(), object2.getKey());
        }
    };

    /**
     * 加载全部的信息
     *
     * @param uri
     * @param surface
     * @return
     */
    public static Map<String, Object> loadResource(@Nullable String uri, @Nullable Surface surface) {
        // Retrieve all metadata.
        Map<String, Object> metadata = new HashMap<>();

        if (uri == null) {
            return metadata;
        }

        FFmpegMediaMetadataRetriever fmmr = new FFmpegMediaMetadataRetriever();
        try {
            if (surface != null) {
                fmmr.setSurface(surface);
            }

            fmmr.setDataSource(uri);

            for (int i = 0; i < Constants.METADATA_KEYS.length; i++) {
                String key = Constants.METADATA_KEYS[i];
                String value = fmmr.extractMetadata(key);

                if (value != null) {
                    metadata.put(key, value);
                }
            }

            // also extract metadata for chapters
            String count = fmmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_CHAPTER_COUNT);

            if (count != null) {
                int chapterCount = Integer.parseInt(count);

                for (int j = 0; j < chapterCount; j++) {
                    for (int i = 0; i < Constants.METADATA_KEYS.length; i++) {
                        String key = Constants.METADATA_KEYS[i];
                        String value = fmmr.extractMetadataFromChapter(key, j);

                        if (value != null) {
                            metadata.put(key, value);
                        }
                    }
                }
            }

            Bitmap b = fmmr.getFrameAtTime();

            if (b != null) {
                Bitmap b2 = fmmr.getFrameAtTime(4000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                if (b2 != null) {
                    b = b2;
                }
            }

            if (b != null) {
                metadata.put(Constants.METADATA_KEYS[Constants.METADATA_KEYS.length - 1], b);
            } else {
                Log.e(MediaRetrieverUtils.class.getName(), "Failed to extract frame");
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally {
            fmmr.release();
        }

        // Sort the list.
        //Collections.sort(metadata, ALPHA_COMPARATOR);

        // Done!
        return metadata;
    }
}
