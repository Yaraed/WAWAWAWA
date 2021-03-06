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

package com.weyee.sdk.router;

import android.content.Context;


/**
 * main模块跳转导航管理类。
 */
public class GPUNavigation extends Navigation {

    private static final String MODULE_NAME = Path.GPU;

    public GPUNavigation(Context context) {
        super(context);
    }

    /**
     * 配置Module
     */
    @Override
    protected String getModuleName() {
        return MODULE_NAME;
    }

    public void toGPUActivity() {
        startActivity("GPU");
    }

    public void toVideoActivity() {
        startActivity("Video");
    }

    public void toAudioActivity() {
        startActivity("Audio");
    }

    public void toMediaActivity() {
        startActivity("Media");
    }

    public void toImageActivity() {
        startActivity("Image");
    }
}
