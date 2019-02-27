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

package com.weyee.sdk.player.entity;

import java.io.Serializable;

/**
 * Created by Taurus on 2018/12/11.
 */
public class TimedTextSource implements Serializable {

    private String path;
    private String mimeType;
    private int flag;

    public TimedTextSource(String path) {
        this.path = path;
    }

    public TimedTextSource(String path, String mimeType) {
        this.path = path;
        this.mimeType = mimeType;
    }

    public TimedTextSource(String path, String mimeType, int flag) {
        this.path = path;
        this.mimeType = mimeType;
        this.flag = flag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
