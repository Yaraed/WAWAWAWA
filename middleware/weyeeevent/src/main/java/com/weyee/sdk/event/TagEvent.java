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

package com.weyee.sdk.event;

/**
 * @author wuqi by 2019/2/11.
 */
final class TagEvent {
    Object mEvent;
    String mTag;

    TagEvent(Object event, String tag) {
        mEvent = event;
        mTag = tag;
    }

    boolean isSameType(final Class eventType, final String tag) {
        return Utils.equals(getEventType(), eventType)
                && Utils.equals(this.mTag, tag);
    }

    Class getEventType() {
        return Utils.getClassFromObject(mEvent);
    }

    @Override
    public String toString() {
        return "event: " + mEvent + ", tag: " + mTag;
    }
}
