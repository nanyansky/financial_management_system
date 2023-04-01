package com.nanyan.annotation;

import com.nanyan.utils.OperationType;

import java.lang.annotation.*;


/**
 * 自定义注解类
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
public @interface OptLog {
    String describe() default "";//操作简述
    OperationType operationType() default OperationType.OTHER;//操作类型
}
