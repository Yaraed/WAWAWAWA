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

package com.weyee.sdk.util.sp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.Utils;
import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author wuqi by 2019/3/12.
 */
class MmkvImpl implements ISharedPreferences {
    private String spName;
    private int mode;

    @Override
    public void init(@Nullable String spName, int mode) {
        // mode只有在多进程访问时，才用得到
        MMKV.initialize(Utils.getApp());
        this.spName = spName;
        this.mode = mode;
    }

    @Override
    public void put(@NonNull String key, String value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, String value, boolean isCommit) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public String getString(@NonNull String key) {
        return getString(key, "");
    }

    @Override
    public String getString(@NonNull String key, String defaultValue) {
        return MMKV.defaultMMKV().decodeString(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, int value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, int value, boolean isCommit) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public int getInt(@NonNull String key) {
        return getInt(key, 0);
    }

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        return MMKV.defaultMMKV().decodeInt(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, long value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, long value, boolean isCommit) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public long getLong(@NonNull String key) {
        return getLong(key, 0L);
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        return MMKV.defaultMMKV().decodeLong(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, float value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, float value, boolean isCommit) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public float getFloat(@NonNull String key) {
        return getFloat(key, 0F);
    }

    @Override
    public float getFloat(@NonNull String key, float defaultValue) {
        return MMKV.defaultMMKV().decodeFloat(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, boolean value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, boolean value, boolean isCommit) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return MMKV.defaultMMKV().decodeBool(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Set<String> value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, Set<String> value, boolean isCommit) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public Set<String> getStringSet(@NonNull String key) {
        return getStringSet(key, Collections.emptySet());
    }

    @Override
    public Set<String> getStringSet(@NonNull String key, Set<String> defaultValue) {
        return MMKV.defaultMMKV().decodeStringSet(key, defaultValue);
    }

    @Override
    public Map<String, ?> getAll() {
        // not impl
        //return MMKV.defaultMMKV().getAll();
        return Collections.emptyMap();
    }

    @Override
    public boolean contains(@NonNull String key) {
        return MMKV.defaultMMKV().containsKey(key);
    }

    @Override
    public void remove(@NonNull String key) {
        remove(key, false);
    }

    @Override
    public void remove(@NonNull String key, boolean isCommit) {
        MMKV.defaultMMKV().removeValueForKey(key);
    }

    @Override
    public void remove(@NonNull String... key) {
        remove(false, key);
    }

    @Override
    public void remove(boolean isCommit, @NonNull String... key) {
        MMKV.defaultMMKV().removeValuesForKeys(key);
    }

    @Override
    public void clear() {
        clear(false);
    }

    @Override
    public void clear(boolean isCommit) {
        MMKV.defaultMMKV().clearAll();
    }

    static class Holder {
        static final MmkvImpl BUS = new MmkvImpl();
    }
}
