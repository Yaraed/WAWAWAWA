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

package com.weyee.sdk.medialoader.bean;

import com.weyee.sdk.medialoader.config.FileLoaderConfig;

/**
 * Created by Taurus on 2017/5/23.
 */

public enum  FileType {

    DOC(new FileProperty(null, FileLoaderConfig.documentMIME)),
    APK(new FileProperty(FileLoaderConfig.apkExtension,null)),
    ZIP(new FileProperty(FileLoaderConfig.zipExtension,null));

    FileProperty property;

    FileType(FileProperty property){
        this.property = property;
    }

    public FileProperty getProperty() {
        return property;
    }

    public void setProperty(FileProperty property) {
        this.property = property;
    }
}
