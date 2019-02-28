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

package com.weyee.poswidget.spinner.editspinner;

/**
 * 监听输入并过滤
 *
 * @author xuexiang
 * @since 2019/1/14 下午10:14
 */
public interface EditSpinnerFilter {
    /**
     * editText输入监听
     * @param keyword
     * @return
     */
    boolean onFilter(String keyword);
}
