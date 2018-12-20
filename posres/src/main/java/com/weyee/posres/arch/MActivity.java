package com.weyee.posres.arch;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.util.BarUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.weyee.posres.R;

public class MActivity extends InnerBaseActivity {

    protected View mContentView;
    protected CoordinatorLayout rootContainer;
    protected Toolbar mToolbar;
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
        mToolbar = findViewById(R.id.toolbar);
        flActivityContainer = findViewById(R.id.activity_container);
        if (layoutResID > 0) {
            flActivityContainer.addView(LayoutInflater.from(this).inflate(layoutResID, flActivityContainer, false), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        setSupportActionBar(mToolbar);
        getToolBar().setDisplayHomeAsUpEnabled(true);

        if (hasToolbar()) {
            abl.setVisibility(View.VISIBLE);
        }

        if (hasStatusBar()) {
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 112);
            BarUtils.addMarginTopEqualStatusBarHeight(rootContainer);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected ActionBar getToolBar() {
        return getSupportActionBar();
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

    protected boolean hasStatusBar() {
        return true;
    }

}
