package com.town.dell.retrofitframe.api.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dell on 2019/4/5.
 * 声明注解（需要元注解）元注解：注解上的注解
 */
//给谁用，给方法用的自定义注解所以用METHOD
@Target(ElementType.METHOD)
//保留到 CLASS RUNTIME 运行时 SOURCE
@Retention(RetentionPolicy.RUNTIME)
public @interface GET {
    String value();
}
