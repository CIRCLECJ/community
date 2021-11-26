package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//表示该注解可以作用在方法上
@Retention(RetentionPolicy.RUNTIME)//程序运行时有效
public @interface LoginRequired {//里面可以什么都不写，它只是起到标识的作用
}