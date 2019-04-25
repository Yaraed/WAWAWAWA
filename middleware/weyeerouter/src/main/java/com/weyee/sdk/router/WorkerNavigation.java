package com.weyee.sdk.router;

import android.content.Context;
import android.os.Bundle;

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

    public void toLocationActivity() {
        startActivity("Location");
    }

    public void toBitmapActivity() {
        startActivity("Bitmap");
    }

    public void toDetailActivity(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        startActivity("Detail", bundle);
    }
}
