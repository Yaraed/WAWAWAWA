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

import java.util.List;

/**
 * Created by Taurus on 2017/5/24.
 */

public class PhotoResult extends BaseResult {

    private List<PhotoFolder> folders;
    private List<PhotoItem> items;

    public PhotoResult() {
    }

    public PhotoResult(List<PhotoFolder> folders, List<PhotoItem> items) {
        this.folders = folders;
        this.items = items;
    }

    public PhotoResult(List<PhotoFolder> folders, List<PhotoItem> items,long totalSize) {
        super(totalSize);
        this.folders = folders;
        this.items = items;
    }

    public List<PhotoFolder> getFolders() {
        return folders;
    }

    public void setFolders(List<PhotoFolder> folders) {
        this.folders = folders;
    }

    public List<PhotoItem> getItems() {
        return items;
    }

    public void setItems(List<PhotoItem> items) {
        this.items = items;
    }
}
