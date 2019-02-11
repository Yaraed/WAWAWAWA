package com.letion.a_ble;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanSettings;
import com.weyee.poscore.base.BaseActivity;
import com.weyee.poscore.di.component.AppComponent;
import com.weyee.posres.arch.RxLiftUtils;
import com.weyee.sdk.api.observer.transformer.Transformer;
import com.weyee.sdk.event.Bus;
import com.weyee.sdk.event.Callback;
import com.weyee.sdk.event.NormalEvent;
import com.weyee.sdk.toast.ToastUtils;

public class BleActivity extends BaseActivity {
    private BleAdapter mAdapter;

    /**
     * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
     *
     * @param appComponent
     */
    @Override
    public void setupActivityComponent(@Nullable AppComponent appComponent) {

    }

    /**
     * 如果initView返回0,框架则不会调用{@link Activity#setContentView(int)}
     *
     * @return
     */
    @Override
    public int getResourceId() {
        return R.layout.activity_ble;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BleAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnAdapterItemClickListener(view -> ToastUtils.show(view.getBleDevice().getName()));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bus.getDefault().subscribeSticky(this, "RxBus", null, new Callback<NormalEvent>() {
            @Override
            public void onEvent(NormalEvent normalEvent) {
                ToastUtils.show("延时收取消息");
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        RxBleClient.create(this).scanBleDevices(new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                        .build(),
                new ScanFilter.Builder()
//                            .setDeviceAddress("B4:99:4C:34:DC:8B")
                        // add custom filters if needed
                        .build())
                .compose(Transformer.switchSchedulers())
                .as(RxLiftUtils.bindLifecycle(this))
                .subscribe(rxBleScanResult -> mAdapter.addScanResult(rxBleScanResult), throwable -> {
                    if (throwable instanceof BleScanException) {
                        Utils.handleException(this, (BleScanException) throwable);
                    }
                });
    }
}
