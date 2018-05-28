package com.cqjtu.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemControllerLog {
    /**
     * 描述业务操作例:datetime-accountId-执行Xxx操作
     * @return
     */
    String description() default "";
}