package com.weyee.poscore.config;

import android.app.Application;
import com.weyee.possupport.AutoSizeConfig;
import com.weyee.sdk.api.RxHttpUtils;
import com.weyee.sdk.api.base.HttpClient;
import com.weyee.sdk.log.Environment;
import com.weyee.sdk.log.LogUtils;
import com.weyee.sdk.multitype.RefreshUtils;
import com.weyee.sdk.player.PlayerUtils;
import com.weyee.sdk.router.RouterManager;
import com.weyee.sdk.toast.ToastUtils;
import com.weyee.sdk.util.sp.SpUtils;

/**
 * <p>全局配置，默认配置
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/11 0011
 */
public class Config {
    public static void init(Application application) {
        AutoSizeConfig.init();
        ToastUtils.init(application);
        LogUtils.init();
        SpUtils.getDefault().init(null, 0);
        RouterManager.init(application, Environment.isDebug());
        RxHttpUtils.getInstance().config().setBaseUrl("https://www.wanandroid.com/")
                .setOkClient(new HttpClient
                        .Builder(application)
                        //全局的请求头信息
                        //.setHeaders(headerMaps)
                        //开启缓存策略(默认false)
                        //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                        //2、在没有网络的时候，去读缓存中的数据。
                        .setCache(true)
                        //全局持久话cookie,保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
                        //不设置的话，默认不对cookie做处理
                        //.setCookieType(new SPCookieStore(this))
                        //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
                        //.setAddInterceptor(null)
                        //全局ssl证书认证
                        //1、信任所有证书,不安全有风险（默认信任所有证书）
                        //.setSslSocketFactory()
                        //2、使用预埋证书，校验服务端证书（自签名证书）
                        //.setSslSocketFactory(cerInputStream)
                        //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                        //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
                        //全局超时配置
                        .setReadTimeout(10)
                        //全局超时配置
                        .setWriteTimeout(10)
                        //全局超时配置
                        .setConnectTimeout(10)
                        //全局是否打开请求log日志
                        .setDebug(true)
                        .build());

        PlayerUtils.init(application);

        RefreshUtils.init();

//        System.out.println("执行了几次了");
//
//        WorkManager.initialize(application,new Configuration.Builder()
//                .setMinimumLoggingLevel(Log.VERBOSE)
//                .build());
    }
}
