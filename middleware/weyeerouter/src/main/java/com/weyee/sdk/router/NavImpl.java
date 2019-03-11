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

import android.content.Context;
import com.blankj.utilcode.util.Utils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuqi by 2019/3/11.
 */
@Singleton
public class NavImpl implements Nav {
    private final Map<Object, Navigation> cacheMap = new ConcurrentHashMap<>();

    @Inject
    public NavImpl() {
    }

    @Override
    public void injectNavigation(Class<?>... services) {
        for (Class<?> service : services) {
            if (cacheMap.containsKey(service.getName())) continue;
            try {
                Constructor c = service.getDeclaredConstructor(Context.class);
                // 调用有参的构造函数，参数暂时使用的Application Context，可能导致转场动画有误
                cacheMap.put(service.getName(), (Navigation) c.newInstance(Utils.getApp()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T extends Navigation> T obtainNavigation(Class<T> service) {
        if (!cacheMap.containsKey(service.getName())) {
            injectNavigation(service);
        }
        return (T) cacheMap.get(service.getName());
    }
}
