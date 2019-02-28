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

package com.wuqi.a_gpuimage.cover;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.weyee.sdk.player.receiver.BaseCover;
import com.wuqi.a_gpuimage.DataInter;
import com.wuqi.a_gpuimage.R;
import com.wuqi.a_gpuimage.R2;

public class CloseCover extends BaseCover {

    @BindView(R2.id.iv_close)
    ImageView mCloseIcon;

    private Unbinder unbinder;

    public CloseCover(Context context) {
        super(context);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
        unbinder = ButterKnife.bind(this, getView());
    }

    @OnClick({R2.id.iv_close})
    public void onViewClick(View view){
        notifyReceiverEvent(DataInter.Event.EVENT_CODE_REQUEST_CLOSE, null);
    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();
        unbinder.unbind();
    }

    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_close_cover, null);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public int getCoverLevel() {
        return levelMedium(10);
    }
}
