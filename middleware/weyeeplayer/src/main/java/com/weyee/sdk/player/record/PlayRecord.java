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
import com.weyee.sdk.player.log.PLog;

/**
 * Created by Taurus on 2018/12/12.
 */
class PlayRecord {

    private final String TAG = "PlayRecord";
    private static PlayRecord i;

    private RecordInvoker mRecordInvoker;

    private PlayRecord(){
        mRecordInvoker = new RecordInvoker(PlayRecordManager.getConfig());
    }

    public static PlayRecord get(){
        if(null==i){
            synchronized (PlayRecord.class){
                if(null==i){
                    i = new PlayRecord();
                }
            }
        }
        return i;
    }

    public int record(DataSource data, int record){
        if(data==null)
            return -1;
        int saveRecord = mRecordInvoker.saveRecord(data, record);
        PLog.d(TAG,"<<Save>> : record = " + record);
        return saveRecord;
    }

    public int reset(DataSource data){
        if(data==null)
            return -1;
        return mRecordInvoker.resetRecord(data);
    }

    public int removeRecord(DataSource data){
        if(data==null)
            return -1;
        return mRecordInvoker.removeRecord(data);
    }

    public int getRecord(DataSource data){
        if(data==null)
            return 0;
        int record = mRecordInvoker.getRecord(data);
        PLog.d(TAG,"<<Get>> : record = " + record);
        return record;
    }

    public void clearRecord(){
        mRecordInvoker.clearRecord();
    }

    public void destroy(){
        clearRecord();
        i = null;
    }

}
