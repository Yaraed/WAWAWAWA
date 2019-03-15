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

public class VideoResult extends BaseResult {

    private List<VideoFolder> folders;
    private List<VideoItem> items;

    public VideoResult() {
    }

    public VideoResult(List<VideoFolder> folders, List<VideoItem> items) {
        this.folders = folders;
        this.items = items;
    }

    public VideoResult(List<VideoFolder> folders, List<VideoItem> items,long totalSize) {
        super(totalSize);
        this.folders = folders;
        this.items = items;
    }

    public List<VideoFolder> getFolders() {
        return folders;
    }

    public void setFolders(List<VideoFolder> folders) {
        this.folders = folders;
    }

    public List<VideoItem> getItems() {
        return items;
    }

    public void setItems(List<VideoItem> items) {
        this.items = items;
    }
}
