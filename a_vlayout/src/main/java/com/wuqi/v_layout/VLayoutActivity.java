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

package com.wuqi.v_layout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.weyee.poscore.base.BaseActivity;
import com.weyee.poscore.di.component.AppComponent;
import com.weyee.possupport.arch.RxLiftUtils;
import com.weyee.sdk.api.rxutil.RxJavaUtils;
import com.weyee.sdk.router.Path;
import com.weyee.sdk.toast.ToastUtils;

import java.util.LinkedList;
import java.util.List;

@Route(path = Path.Virtual + "vlayout")
public class VLayoutActivity extends BaseActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.tvCLick)
    TextView tvCLick;

    @Override
    public void setupActivityComponent(@Nullable AppComponent appComponent) {

    }

    @Override
    public int getResourceId() {
        return R.layout.activity_vlayout;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(recycledViewPool);
        recycledViewPool.setMaxRecycledViews(0, 20);
        layoutManager.setRecycleOffset(300);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @SuppressLint({"AutoDispose", "DefaultLocale"})
    @OnClick(R2.id.tvCLick)
    public void onViewClicked() {
        RxJavaUtils.countDown(1)
                .as(RxLiftUtils.bindLifecycle(this))
                .subscribe(aLong -> ToastUtils.show(String.format("这个是第%d次点击了", aLong)));
    }
}
