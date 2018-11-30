package com.weyee.poscore.base.delegate;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created by liu-feng on 2017/6/5.
 */
public interface ActivityDelegate extends Parcelable {

    String ACTIVITY_DELEGATE = "activity_delegate";

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();
}
