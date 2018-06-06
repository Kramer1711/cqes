package com.cqjtu.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })//作用范围：成员方法
@Retention(RetentionPolicy.RUNTIME)//保存时间：运行时保存
public @interface ControllerLog {
    /**
     * 描述业务操作例:datetime-accountId-执行Xxx操作
     * @return
     */
    String description() default "";
}