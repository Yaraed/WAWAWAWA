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
import android.net.Uri;
import android.provider.MediaStore;
import androidx.loader.content.Loader;
import com.weyee.sdk.medialoader.bean.PhotoFolder;
import com.weyee.sdk.medialoader.bean.PhotoItem;
import com.weyee.sdk.medialoader.bean.PhotoResult;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_ID;
import static android.provider.MediaStore.MediaColumns.*;

/**
 * Created by Taurus on 2017/5/23.
 */

public abstract class OnPhotoLoaderCallBack extends BaseLoaderCallBack<PhotoResult> {

    @Override
    public void onLoadFinish(Loader<Cursor> loader, Cursor data) {
        List<PhotoFolder> folders = new ArrayList<>();
        List<PhotoItem> allPhotos = new ArrayList<>();
        if(data == null){
            onResult(new PhotoResult(folders,allPhotos));
            return;
        }
        PhotoFolder folder;
        PhotoItem item;
        long sum_size = 0;
        while (data.moveToNext()) {
            String folderId = data.getString(data.getColumnIndexOrThrow(BUCKET_ID));
            String folderName = data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME));
            int imageId = data.getInt(data.getColumnIndexOrThrow(_ID));
            String name = data.getString(data.getColumnIndexOrThrow(DISPLAY_NAME));
            long size = data.getLong(data.getColumnIndexOrThrow(SIZE));
            String path = data.getString(data.getColumnIndexOrThrow(DATA));
            long modified = data.getLong(data.getColumnIndexOrThrow(DATE_MODIFIED));
            folder = new PhotoFolder();
            folder.setId(folderId);
            folder.setName(folderName);
            item = new PhotoItem(imageId,name,path,size,modified);
            if(folders.contains(folder)){
                folders.get(folders.indexOf(folder)).addItem(item);
            }else{
                folder.setCover(path);
                folder.addItem(item);
                folders.add(folder);
            }
            allPhotos.add(item);
            sum_size += size;
        }
        onResult(new PhotoResult(folders,allPhotos,sum_size));
    }

    @Override
    public String[] getSelectProjection() {
        String[] PROJECTION = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_MODIFIED
        };
        return PROJECTION;
    }

    @Override
    public Uri getQueryUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
}
