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

package com.weyee.sdk.player.record;

import com.weyee.sdk.player.entity.DataSource;

/**
 * Created by Taurus on 2018/12/12.
 */
public class RecordInvoker {

    private OnRecordCallBack mCallBack;

    private RecordCache mRecordCache;

    public RecordInvoker(PlayRecordManager.RecordConfig config){
        this.mCallBack = config.getOnRecordCallBack();
        mRecordCache = new RecordCache(config.getMaxRecordCount());
    }

    public int saveRecord(DataSource dataSource, int record) {
        if(mCallBack!=null){
            return mCallBack.onSaveRecord(dataSource, record);
        }
        return mRecordCache.putRecord(getKey(dataSource), record);
    }

    public int getRecord(DataSource dataSource) {
        if(mCallBack!=null){
            return mCallBack.onGetRecord(dataSource);
        }
        return mRecordCache.getRecord(getKey(dataSource));
    }

    public int resetRecord(DataSource dataSource) {
        if(mCallBack!=null){
            return mCallBack.onResetRecord(dataSource);
        }
        return mRecordCache.putRecord(getKey(dataSource), 0);
    }

    public int removeRecord(DataSource dataSource) {
        if(mCallBack!=null){
            return mCallBack.onRemoveRecord(dataSource);
        }
        return mRecordCache.removeRecord(getKey(dataSource));
    }

    public void clearRecord() {
        if(mCallBack!=null){
            mCallBack.onClearRecord();
            return;
        }
        mRecordCache.clearRecord();
    }

    String getKey(DataSource dataSource){
        return PlayRecordManager.getKey(dataSource);
    }

}
