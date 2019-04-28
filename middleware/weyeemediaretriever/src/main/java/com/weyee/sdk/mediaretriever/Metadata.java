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

package com.weyee.sdk.mediaretriever;

/**
 * 元数据信息
 *
 * @author wuqi by 2019/3/13.
 */
public class Metadata {
    private String mKey;
    private Object mValue;

    public Metadata(String key, Object value) {
        mKey = key;
        mValue = value;
    }

    /**
     * @return the mKey
     */
    public String getKey() {
        return mKey;
    }

    /**
     * @param mKey the mKey to set
     */
    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    /**
     * @return the mValue
     */
    public Object getValue() {
        return mValue;
    }

    /**
     * @param mValue the mValue to set
     */
    public void setValue(Object mValue) {
        this.mValue = mValue;
    }
}
