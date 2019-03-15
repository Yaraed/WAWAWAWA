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

import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import androidx.loader.content.Loader;
import com.weyee.sdk.medialoader.bean.FileItem;
import com.weyee.sdk.medialoader.bean.FileProperty;
import com.weyee.sdk.medialoader.bean.FileResult;
import com.weyee.sdk.medialoader.bean.FileType;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.DATE_MODIFIED;
import static android.provider.MediaStore.Files.FileColumns.MIME_TYPE;

/**
 * Created by Taurus on 2017/5/23.
 */

public abstract class OnFileLoaderCallBack extends BaseFileLoaderCallBack<FileResult> {

    public OnFileLoaderCallBack() {
    }

    public OnFileLoaderCallBack(FileType type) {
        super(type);
    }

    public OnFileLoaderCallBack(FileProperty property) {
        super(property);
    }

    @Override
    public void onLoadFinish(Loader<Cursor> loader, Cursor data) {
        List<FileItem> result = new ArrayList<>();
        FileItem item;
        long sum_size = 0;
        while (data.moveToNext()) {
            item = new FileItem();
            int audioId = data.getInt(data.getColumnIndexOrThrow(BaseColumns._ID));
            String path = data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            long size = data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE));
            String name = data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
            String mime = data.getString(data.getColumnIndexOrThrow(MIME_TYPE));
            long modified = data.getLong(data.getColumnIndexOrThrow(DATE_MODIFIED));
            item.setId(audioId);
            item.setDisplayName(name);
            item.setPath(path);
            item.setSize(size);
            item.setMime(mime);
            item.setModified(modified);
            result.add(item);
            sum_size += size;
        }
        onResult(new FileResult(sum_size,result));
    }

}
