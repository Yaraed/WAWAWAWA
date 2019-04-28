package com.weyee.sdk.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * @author wuqi by 2019/4/28.
 */
@Documented
@Retention(CLASS)
@Target({PACKAGE})
public @interface ApiTag {
    String value() default "";
}