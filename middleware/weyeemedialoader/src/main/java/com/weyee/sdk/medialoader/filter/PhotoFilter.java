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

package com.weyee.sdk.medialoader.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Taurus on 2017/5/24.
 */

public class PhotoFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        if(pathname.length()<=0)
            return false;
        String name = pathname.getName();
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(i);
            if (name.equalsIgnoreCase(".jpg")
                    || name.equalsIgnoreCase(".jpeg")
                    || name.equalsIgnoreCase(".png")
                    || name.equalsIgnoreCase(".gif")
                    || name.equalsIgnoreCase(".bmp")) {
                return true;
            }
        }
        return false;
    }
}
