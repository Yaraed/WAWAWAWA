package com.letion.app.config;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.blankj.utilcode.util.ProcessUtils;

/**
 * 通过开启服务来完成Application中的耗时操作
 *
 * @author wuqi by 2019/5/27.
 */
public class InitializeService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_INIT_WHEN_APP_CREATE = "ACTION.APP.INIT";

    /**
     * 启动调用
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    public InitializeService() {
        super("InitializeService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    /**
     * 启动初始化操作
     */
    private void performInit() {
        if (ProcessUtils.isMainProcess()) {
            com.weyee.poscore.config.Config.init(getApplication());

            //DoraemonKit.install(application);


        }
//        if (LeakCanary.isInAnalyzerProcess(getApplication())) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(getApplication());
    }
}
