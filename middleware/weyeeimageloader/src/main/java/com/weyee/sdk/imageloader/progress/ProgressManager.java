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

package com.weyee.sdk.imageloader.progress;

import android.text.TextUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片加载进度监听
 *
 * @author wuqi by 2019/3/5.
 */
public class ProgressManager {
    private static Map<String, OnProgressListener> listenersMap = Collections.synchronizedMap(new HashMap<>());
    private static OkHttpClient okHttpClient;

    private ProgressManager() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(chain -> {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        return response.newBuilder()
                                .body(new ProgressResponseBody(request.url().toString(), LISTENER, response.body()))
                                .build();
                    })
                    .build();
        }
        return okHttpClient;
    }

    private static final ProgressResponseBody.InternalProgressListener LISTENER = (url, bytesRead, totalBytes) -> {
        OnProgressListener onProgressListener = getProgressListener(url);
        if (onProgressListener != null) {
            int percentage = (int) ((bytesRead * 1f / totalBytes) * 100f);
            boolean isComplete = percentage >= 100;
            onProgressListener.onProgress(isComplete, percentage, bytesRead, totalBytes);
            if (isComplete) {
                removeListener(url);
            }
        }
    };

    public static OnProgressListener getProgressListener(String url) {
        if (TextUtils.isEmpty(url) || listenersMap == null || listenersMap.size() == 0) {
            return null;
        }

        OnProgressListener listenerWeakReference = listenersMap.get(url);
        if (listenerWeakReference != null) {
            return listenerWeakReference;
        }
        return null;
    }

    public static void addListener(String url, OnProgressListener listener) {
        if (!TextUtils.isEmpty(url) && listener != null) {
            listenersMap.put(url, listener);
            listener.onProgress(false, 1, 0, 0);
        }
    }

    public static void removeListener(String url) {
        if (!TextUtils.isEmpty(url)) {
            listenersMap.remove(url);
        }
    }
}
