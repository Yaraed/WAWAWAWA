package com.weyee.poscore.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by liu-feng on 2017/6/5.
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
