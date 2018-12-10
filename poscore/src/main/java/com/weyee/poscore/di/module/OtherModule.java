package com.weyee.poscore.di.module;

import dagger.Module;

/**
 * ================================================
 * 框架独创的建造者模式 {@link Module},可向框架中注入外部配置的自定义参数
 * <p>
 * Created by liu-feng on 2017/6/5.
 */
@Module
public class OtherModule {
    private OtherModule(Builder builder) {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {
        }

        public OtherModule build() {
            return new OtherModule(this);
        }


    }


}
