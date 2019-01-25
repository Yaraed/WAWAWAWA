/*
 * Copyright (c) 2018 liu-feng
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weyee.sdk.api.rxutil.subscribe;

import com.weyee.sdk.log.LogUtils;
import io.reactivex.functions.Consumer;

/**
 * <p>简单的出错处理（把错误打印出来）
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/21 0021
 */
public class SimpleThrowableAction implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        LogUtils.d("订阅发生错误！", throwable);
    }
}
