package com.weyee.poscore.base;

import android.app.Application;
import android.content.Context;

import com.weyee.poscore.base.delegate.AppDelegate;
import com.weyee.poscore.di.component.AppComponent;

/**
 * Created by liu-feng on 2017/6/5.
 */
public class BaseApplication extends Application implements App {

    private AppDelegate mAppDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate();
    }

    /**
     * Android系统提供了一些回调来通知当前应用的内存使用情况，通常来说，当所有的background应用都被kill掉的时候，forground应用会收到onLowMemory()的回调。
     * 在这种情况下，需要尽快释放当前应用的非必须的内存资源，从而确保系统能够继续稳定运行
     * onTrimMemory()的回调是在API 14才被加进来的，对于老的版本，你可以使用onLowMemory)回调来进行兼容。
     * onLowMemory相当与{@link TRIM_MEMORY_COMPLETE}
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mAppDelegate != null)
            this.mAppDelegate.onLowMemory();
    }

    /**
     * Android系统从4.0开始还提供了onTrimMemory()的回调，当系统内存达到某些条件的时候，所有正在运行的应用都会收到这个回调，同时在这个回调里面会传递以下的参数，代表不同的内存使用情况，
     * 收到onTrimMemory()回调的时候，需要根据传递的参数类型进行判断，合理的选择释放自身的一些内存占用，一方面可以提高系统的整体运行流畅度，另外也可以避免自己被系统判断为优先需要杀掉的应用
     * @param level
     *        @TRIM_MEMORY_UI_HIDDEN 你的应用程序的所有UI界面被隐藏了，即用户点击了Home键或者Back键退出应用，导致应用的UI界面完全不可见。这个时候应该释放一些不可见的时候非必须的资源
     *
     * 当程序正在前台运行的时候，可能会接收到从onTrimMemory()中返回的下面的值之一：
     *        @RIM_MEMORY_RUNNING_MODERATE 你的应用正在运行并且不会被列为可杀死的。但是设备此时正运行于低内存状态下，系统开始触发杀死LRU Cache中的Process的机制。
     *
     *        @TRIM_MEMORY_RUNNING_LOW 你的应用正在运行且没有被列为可杀死的。但是设备正运行于更低内存的状态下，你应该释放不用的资源用来提升系统性能。
     *
     *        @TRIM_MEMORY_RUNNING_CRITICAL 你的应用仍在运行，但是系统已经把LRU Cache中的大多数进程都已经杀死，因此你应该立即释放所有非必须的资源。如果系统不能回收到足够的RAM数量，系统将会清除所有的LRU缓存中的进程，并且开始杀死那些之前被认为不应该杀死的进程，例如那个包含了一个运行态Service的进程。
     *
     * 当应用进程退到后台正在被Cached的时候，可能会接收到从onTrimMemory()中返回的下面的值之一：
     *        @TRIM_MEMORY_BACKGROUND 系统正运行于低内存状态并且你的进程正处于LRU缓存名单中最不容易杀掉的位置。尽管你的应用进程并不是处于被杀掉的高危险状态，系统可能已经开始杀掉LRU缓存中的其他进程了。你应该释放那些容易恢复的资源，以便于你的进程可以保留下来，这样当用户回退到你的应用的时候才能够迅速恢复。
     *
     *        @TRIM_MEMORY_MODERATE 系统正运行于低内存状态并且你的进程已经已经接近LRU名单的中部位置。如果系统开始变得更加内存紧张，你的进程是有可能被杀死的。
     *
     *        @TRIM_MEMORY_COMPLETE 系统正运行于低内存的状态并且你的进程正处于LRU名单中最容易被杀掉的位置。你应该释放任何不影响你的应用恢复状态的资源。
     *
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (mAppDelegate != null)
            this.mAppDelegate.onTrimMemory(level);
    }

    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    @Override
    public AppComponent getAppComponent() {
        return mAppDelegate.getAppComponent();
    }
}
