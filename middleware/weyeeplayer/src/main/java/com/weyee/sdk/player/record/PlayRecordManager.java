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
 *
 * Play Record Manager for user use it.
 *
 */
public class PlayRecordManager {

    private static final int DEFAULT_MAX_RECORD_COUNT = 200;

    private static RecordKeyProvider recordKeyProvider;

    private static RecordConfig config;

    public static void setRecordConfig(RecordConfig recordConfig){
        config = recordConfig;
        checkDefaultConfig();
        recordKeyProvider = config.getRecordKeyProvider();
    }

    private static void checkDefaultConfig(){
        if(config==null)
            config = new RecordConfig.Builder()
                    .setMaxRecordCount(DEFAULT_MAX_RECORD_COUNT)
                    .setRecordKeyProvider(new DefaultRecordKeyProvider()).build();
    }

    static RecordConfig getConfig(){
        checkDefaultConfig();
        return config;
    }

    static RecordKeyProvider getRecordKeyProvider(){
        if(recordKeyProvider==null)
            return new DefaultRecordKeyProvider();
        return recordKeyProvider;
    }

    public static String getKey(DataSource dataSource){
        return getRecordKeyProvider().generatorKey(dataSource);
    }

    /**
     * remove record by DataSource.
     * @param dataSource
     * @return
     */
    public static int removeRecord(DataSource dataSource){
        return PlayRecord.get().removeRecord(dataSource);
    }

    /**
     * clear record memory cache.
     */
    public static void clearRecord(){
        PlayRecord.get().clearRecord();
    }

    /**
     * clear and destroy memory cache.
     */
    public static void destroyCache(){
        PlayRecord.get().destroy();
    }

    /**
     * record config, setting max cache count and key provider.
     */
    public static class RecordConfig{

        private int maxRecordCount;
        private RecordKeyProvider recordKeyProvider;
        private OnRecordCallBack onRecordCallBack;

        RecordConfig(int maxRecordCount, RecordKeyProvider recordKeyProvider, OnRecordCallBack onRecordCallBack) {
            this.maxRecordCount = maxRecordCount;
            this.recordKeyProvider = recordKeyProvider;
            this.onRecordCallBack = onRecordCallBack;
        }

        public int getMaxRecordCount() {
            return maxRecordCount;
        }

        public RecordKeyProvider getRecordKeyProvider() {
            return recordKeyProvider;
        }

        public OnRecordCallBack getOnRecordCallBack() {
            return onRecordCallBack;
        }

        public static class Builder{

            private int maxRecordCount;
            private RecordKeyProvider recordKeyProvider;
            private OnRecordCallBack onRecordCallBack;

            public int getMaxRecordCount() {
                return maxRecordCount;
            }

            public Builder setMaxRecordCount(int maxRecordCount) {
                this.maxRecordCount = maxRecordCount;
                return this;
            }

            public RecordKeyProvider getRecordKeyProvider() {
                return recordKeyProvider;
            }

            public Builder setRecordKeyProvider(RecordKeyProvider recordKeyProvider) {
                this.recordKeyProvider = recordKeyProvider;
                return this;
            }

            public OnRecordCallBack getOnRecordCallBack() {
                return onRecordCallBack;
            }

            public Builder setOnRecordCallBack(OnRecordCallBack onRecordCallBack) {
                this.onRecordCallBack = onRecordCallBack;
                return this;
            }

            public RecordConfig build(){
                return new RecordConfig(maxRecordCount, recordKeyProvider, onRecordCallBack);
            }

        }

    }

}
