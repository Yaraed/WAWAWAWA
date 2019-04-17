package com.weyee.sdk.router;

import android.content.Context;

/**
 * @author wuqi by 2019/4/17.
 */
public class WorkerNavigation extends Navigation {
    public WorkerNavigation(Context context) {
        super(context);
    }

    @Override
    protected String getModuleName() {
        return Path.Service;
    }

    public void toWorkerActivity() {
        startActivity("Worker");
    }

    public void toWanActivity() {
        startActivity("Wan");
    }
}
