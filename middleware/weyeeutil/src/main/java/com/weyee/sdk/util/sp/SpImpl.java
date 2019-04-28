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
import com.blankj.utilcode.util.SPUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 默认实现
 *
 * @author wuqi by 2019/3/12.
 */
class SpImpl implements ISharedPreferences {
    private String spName;
    private int mode;

    @Override
    public void init(@Nullable String spName, int mode) {
        SPUtils.getInstance(spName, mode);
        this.spName = spName;
        this.mode = mode;
    }

    @Override
    public void put(@NonNull String key, String value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, String value, boolean isCommit) {
        SPUtils.getInstance(spName, mode).put(key, value, isCommit);
    }

    @Override
    public String getString(@NonNull String key) {
        return getString(key, "");
    }

    @Override
    public String getString(@NonNull String key, String defaultValue) {
        return SPUtils.getInstance(spName, mode).getString(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, int value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, int value, boolean isCommit) {
        SPUtils.getInstance(spName, mode).put(key, value, isCommit);
    }

    @Override
    public int getInt(@NonNull String key) {
        return getInt(key, 0);
    }

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        return SPUtils.getInstance(spName, mode).getInt(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, long value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, long value, boolean isCommit) {
        SPUtils.getInstance(spName, mode).put(key, value, isCommit);
    }

    @Override
    public long getLong(@NonNull String key) {
        return getLong(key, 0L);
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        return SPUtils.getInstance(spName, mode).getLong(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, float value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, float value, boolean isCommit) {
        SPUtils.getInstance(spName, mode).put(key, value, isCommit);
    }

    @Override
    public float getFloat(@NonNull String key) {
        return getFloat(key, 0F);
    }

    @Override
    public float getFloat(@NonNull String key, float defaultValue) {
        return SPUtils.getInstance(spName, mode).getFloat(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, boolean value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, boolean value, boolean isCommit) {
        SPUtils.getInstance(spName, mode).put(key, value, isCommit);
    }

    @Override
    public boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return SPUtils.getInstance(spName, mode).getBoolean(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Set<String> value) {
        put(key, value, false);
    }

    @Override
    public void put(@NonNull String key, Set<String> value, boolean isCommit) {
        SPUtils.getInstance(spName, mode).put(key, value, isCommit);
    }

    @Override
    public Set<String> getStringSet(@NonNull String key) {
        return getStringSet(key, Collections.emptySet());
    }

    @Override
    public Set<String> getStringSet(@NonNull String key, Set<String> defaultValue) {
        return SPUtils.getInstance(spName, mode).getStringSet(key, defaultValue);
    }

    @Override
    public Map<String, ?> getAll() {
        return SPUtils.getInstance(spName,mode).getAll();
    }

    @Override
    public boolean contains(@NonNull String key) {
        return SPUtils.getInstance(spName, mode).contains(key);
    }

    @Override
    public void remove(@NonNull String key) {
        remove(key, false);
    }

    @Override
    public void remove(@NonNull String key, boolean isCommit) {
        SPUtils.getInstance(spName, mode).remove(key, isCommit);
    }

    @Override
    public void remove(@NonNull String... key) {
        remove(false, key);
    }

    @Override
    public void remove(boolean isCommit, @NonNull String... key) {
        for (String keyword : key) {
            remove(keyword, isCommit);
        }
    }

    @Override
    public void clear() {
        clear(false);
    }

    @Override
    public void clear(boolean isCommit) {
        SPUtils.getInstance(spName, mode).clear(isCommit);
    }

    static class Holder {
        static final SpImpl BUS = new SpImpl();
    }
}
