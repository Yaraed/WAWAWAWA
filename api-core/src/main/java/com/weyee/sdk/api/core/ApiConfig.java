package com.weyee.sdk.api.core;

import okhttp3.Interceptor;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>api 配置类。</p>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class ApiConfig {

    public ApiConfig() {
    }

    /**
     * 是否是debug 模式。debug 模式会打印相关开发日志。
     */
    public static final boolean isDebug = true;

    /**
     * http 错误统一
     */
    public static ErrorHintAble errorHintAble;

    /**
     * 前缀域名
     */
    private String host = "";
    /**
     * 超时时间(秒)。
     */
    private long timeout = 30;

    /**
     * json解析
     */
    private Converter.Factory covertFactory = GsonConverterFactory.create();

    private List<Interceptor> interceptors = new ArrayList<>();
    /**
     * 拦截器
     */
    private Interceptor interceptor;

    public void setErrorHintAble(ErrorHintAble errorHintAble) {
        ApiConfig.errorHintAble = null;
        ApiConfig.errorHintAble = errorHintAble;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Converter.Factory getCovertFactory() {
        return covertFactory;
    }

    public void setCovertFactory(Converter.Factory covertFactory) {
        this.covertFactory = covertFactory;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
        interceptors.add(interceptor);
    }
}
