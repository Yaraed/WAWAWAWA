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

package com.weyee.sdk.medialoader.callback;

import android.net.Uri;
import android.provider.MediaStore;
import com.weyee.sdk.medialoader.bean.FileProperty;
import com.weyee.sdk.medialoader.bean.FileType;

/**
 * Created by Taurus on 2017/5/23.
 */

public abstract class BaseFileLoaderCallBack<T> extends BaseLoaderCallBack<T> {

    public static final String VOLUME_NAME = "external";

    private FileProperty mProperty;

    public BaseFileLoaderCallBack(){
        this(new FileProperty(null,null));
    }

    public BaseFileLoaderCallBack(FileType type){
        this(type.getProperty());
    }

    public BaseFileLoaderCallBack(FileProperty property){
        this.mProperty = property;
    }

    @Override
    public Uri getQueryUri() {
        return MediaStore.Files.getContentUri(VOLUME_NAME);
    }

    @Override
    public String getSelections() {
        if(mProperty!=null)
            return mProperty.createSelection();
        return null;
    }

    @Override
    public String[] getSelectProjection() {
        return new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.DATE_MODIFIED
        };
    }

    @Override
    public String[] getSelectionsArgs() {
        if(mProperty!=null)
            return mProperty.createSelectionArgs();
        return null;
    }
}
