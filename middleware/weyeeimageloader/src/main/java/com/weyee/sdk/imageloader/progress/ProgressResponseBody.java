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

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.*;

import java.io.IOException;

/**
 * @author wuqi by 2019/3/5.
 */
class ProgressResponseBody extends ResponseBody {

    /**
     * 主线程中实现进度监听
     */
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    private String url;
    private ProgressResponseBody.InternalProgressListener internalProgressListener;

    private ResponseBody responseBody;
    private BufferedSource bufferedSource;

    ProgressResponseBody(String url, ProgressResponseBody.InternalProgressListener internalProgressListener, ResponseBody responseBody) {
        this.url = url;
        this.internalProgressListener = internalProgressListener;
        this.responseBody = responseBody;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead;
            long lastTotalBytesRead;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += (bytesRead == -1) ? 0 : bytesRead;

                if (internalProgressListener != null && lastTotalBytesRead != totalBytesRead) {
                    lastTotalBytesRead = totalBytesRead;
                    mainThreadHandler.post(() -> internalProgressListener.onProgress(url, totalBytesRead, contentLength()));
                }
                return bytesRead;
            }
        };
    }

    interface InternalProgressListener {
        void onProgress(String url, long bytesRead, long totalBytes);
    }
}
