package com.cqjtu.annotation;

import java.lang.annotation.*; 
/**
 * 自定义注解 拦截Service
 *
 */
@Target({ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceLog {
	  String description()  default "";
}