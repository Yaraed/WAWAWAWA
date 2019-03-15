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

package com.weyee.sdk.medialoader.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/23.
 */

public class FileLoaderConfig {

    public static List<String> documentMIME = new ArrayList<String>(){
        {
            add("text/plain");
            add("application/pdf");
            add("application/msword");
            add("application/vnd.ms-excel");
            add("application/vnd.ms-powerpoint");
            add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        }
    };

    public static List<String> zipExtension = new ArrayList<String>(){
        {
            add(".zip");
            add(".rar");
        }
    };

    public static List<String> apkExtension = new ArrayList<String>(){
        {
            add(".apk");
        }
    };

}
