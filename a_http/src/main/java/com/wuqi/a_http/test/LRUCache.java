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

package com.wuqi.a_http.test;

import java.util.LinkedHashMap;

/**
 * @author wuqi by 2019/2/20.
 */
public class LRUCache extends LinkedHashMap<Long, String> {
    private static final int MAX_ENTRIES = 3;

    public LRUCache() {
        super(MAX_ENTRIES, 0.75f, true);
    }

    @Override
    protected boolean removeEldestEntry(Entry<Long, String> eldest) {
        return size() > MAX_ENTRIES;
    }
}
