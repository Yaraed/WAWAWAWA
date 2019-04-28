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

package com.weyee.sdk.router;

/**
 * @author wuqi by 2019/3/11.
 */
public interface Nav {
    /**
     * 注入Navigation
     *
     * @param services
     */
    void injectNavigation(Class<?>... services);


    /**
     * 根据传入的Class获取对应的Navigation
     *
     * @param service
     * @param <T>
     * @return
     */
    <T extends Navigation> T obtainNavigation(Class<T> service);
}
