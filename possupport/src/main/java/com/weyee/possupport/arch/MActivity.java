package com.weyee.possupport.arch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ReflectUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.weyee.possupport.R;
import com.weyee.possupport.headerview.MHeaderViewAble;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MActivity extends InnerBaseActivity {

    protected View mContentView;
    protected CoordinatorLayout rootContainer;
    protected MHeaderViewAble mHeaderViewAble;
    protected AppBarLayout abl;
    protected FrameLayout flActivityContainer;

    @SuppressLint("ResourceType")
    @Override
    public void setContentView(int layoutResID) {
        if (canSwipeBack()) {
            SlidrConfig config = new SlidrConfig.Builder()
                    .position(SlidrPosition.LEFT)
                    .sensitivity(1f)
                    .scrimColor(Color.BLACK)
                    .scrimStartAlpha(0.8f)
                    .scrimEndAlpha(0f)
                    .velocityThreshold(2400)
                    .distanceThreshold(0.25f)
                    .edge(true)
                    .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
                    .build();
            Slidr.attach(this, config);
        }
        mContentView = LayoutInflater.from(this).inflate(R.layout.activity_back_base, null);
        super.setContentView(mContentView);
        rootContainer = findViewById(R.id.root_container);
        abl = findViewById(R.id.abl);
        mHeaderViewAble = findViewById(R.id.toolbar);
        flActivityContainer = findViewById(R.id.activity_container);
        if (layoutResID > 0) {
            flActivityContainer.addView(LayoutInflater.from(this).inflate(layoutResID, flActivityContainer, false), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (hasToolbar()) {
            abl.setVisibility(View.VISIBLE);
            initHeaderView();
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 112);
            BarUtils.addMarginTopEqualStatusBarHeight(rootContainer);
        }
    }

    /**
     * 初始化头布局
     */
    protected void initHeaderView() {
        mHeaderViewAble.setTitle((String) getTitle());
        mHeaderViewAble.setMenuLeftBackIcon(R.mipmap.ic_back);
        mHeaderViewAble.setOnClickLeftMenuBackListener(v -> finish());
    }

    @NonNull
    public Context getContext() {
        return this;
    }

    @NonNull
    protected MHeaderViewAble getHeaderView() {
        return mHeaderViewAble;
    }

    /**
     * disable or enable drag back
     *
     * @return
     */
    protected boolean canSwipeBack() {
        return true;
    }

    protected boolean hasToolbar() {
        return true;
    }

    /**
     * 另外一种获取Presenter的方法
     * 弃用它，why？：因为每次调用都需要重新实例化对象，待优化
     * @param object
     * @param <T>
     * @return
     */
    @Nullable
    @Deprecated
    public static <T> T getPresenter(Object object) {
        Type type = object.getClass().getGenericSuperclass();
        if (type == null) {
            return null;
        } else if (!(type instanceof ParameterizedType)) {
            return null;
        } else {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Class clazz = (Class)parameterizedType.getActualTypeArguments()[0];
            if (clazz == null) {
                return null;
            } else {
                return ReflectUtils.reflect(clazz).newInstance().get();
            }
        }
    }
}
