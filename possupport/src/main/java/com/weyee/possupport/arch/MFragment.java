package com.weyee.possupport.arch;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * <p>x生命周期管理Fragment
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/20 0020
 */
public class MFragment extends Fragment {
    private Context context;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public Context getContext() {
        final Context context = super.getContext();
        return context == null ? this.context : context;
    }
}
