package com.weyee.sdk.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

import static com.weyee.possupport.arch.Utils.*;

/**
 * <p>跳转导航管理基类
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/18 0018
 */
public abstract class Navigation {
    private Context context;

    public Context getContext() {
        return context;
    }

    public Navigation(Context context) {
        this.context = context;
    }

    /**
     * 配置Module
     */
    protected abstract String getModuleName();

    protected void startActivity(String routePath) {
        startActivity(routePath, -1);
    }

    protected void startActivity(String routePath, int requestCode) {
        startActivity(routePath, null, requestCode);
    }

    protected void startActivity(String routePath, Bundle bundle) {
        startActivity(routePath, bundle, -1);
    }

    protected void startActivity(String routePath, Bundle bundle, int requestCode) {
        startActivity(ANIM_STYLE_SLID_IN_RIGHT, routePath, bundle, requestCode);
    }

    protected void startActivity(int activityAnimStyle, String routePath) {
        startActivity(activityAnimStyle, routePath, -1);
    }

    protected void startActivity(int activityAnimStyle, String routePath, int requestCode) {
        startActivity(activityAnimStyle, routePath, null, requestCode);
    }

    protected void startActivity(int activityAnimStyle, String routePath, Bundle bundle) {
        startActivity(activityAnimStyle, routePath, bundle, -1);
    }

    protected void startActivity(int activityAnimStyle,String routePath, Bundle bundle,int requestCode) {
        startActivity( context, getRouterPath(routePath), bundle, activityAnimStyle, requestCode);
    }

    /**
     * 跳转页面
     *
     * @param context
     * @param routePath
     * @param bundle
     * @param requestCode
     */
    public static void startActivity(Context context,String routePath,Bundle bundle,int activityAnimStyle,int requestCode) {
        Log.i("Navigation", "routePath-->" + routePath);

        int enterAnim = R.anim.slide_in_right;
        int exitAnim = R.anim.slide_out_right;
        if (activityAnimStyle == ANIM_STYLE_SLID_IN_RIGHT) {
            enterAnim = R.anim.slide_in_right;
            exitAnim = R.anim.slide_out_right;

        } else if (activityAnimStyle == ANIM_STYLE_SLID_IN_BOTTOM) {
            enterAnim = R.anim.slide_in_bottom;
            exitAnim = R.anim.slide_out_bottom;

        } else if (activityAnimStyle == ANIM_STYLE_SLID_IN_RIGHT_OUT_LEFT) {
            enterAnim = R.anim.slide_in_right;
            exitAnim = R.anim.slide_out_left;

        } else if (activityAnimStyle == ANIM_STYLE_NONE) {
            enterAnim = 0;
            exitAnim = 0;
        }

        if (bundle == null) {
            bundle = new Bundle();
        }

        bundle.putInt(KEY_ACTIVITY_ANIM_STYLE, activityAnimStyle);

        Postcard postcard = ARouter.getInstance()
                .build(routePath)
                .with(bundle);
        if (activityAnimStyle != -1) {
            postcard.withTransition(enterAnim, exitAnim);
        }

        if (context instanceof Activity) {
            postcard.navigation((Activity) context, requestCode);
        }else{
            postcard.navigation(context);
        }
    }

    /**
     * 获取完整路由地址
     *
     * @param pageName
     */
    protected String getRouterPath(String pageName) {
        return getModuleName() + pageName;
    }

    protected Fragment getFragemnt(String path) {
        return  (Fragment) ARouter.getInstance().build(getRouterPath(path)).navigation();
    }

    protected Fragment getCustomFragemnt(String path,String rootPath) {
        return  (Fragment) ARouter.getInstance().build(rootPath+(path)).navigation();
    }
}
