package com.weyee.sdk.log;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
public interface IEnvironment {
    /**
     * 开发环境
     */
    int DEV = 0x1;
    /**
     * 测试环境
     */
    int QA = 0x11;
    /**
     * 预发布环境
     */
    int STAGE = 0x111;
    /**
     * 生产环境
     */
    int RELEASE = 0x1111;

    /**
     * 是否debug模式,默认实现{@link BuildConfig#DEBUG}
     *
     * @return
     */
    default boolean debug() {return BuildConfig.DEBUG;}

    /**
     * 默认环境
     *
     * @return
     */
    default int environment() { return DEV; }
}
