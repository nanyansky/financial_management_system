package com.nanyan.annotation;

import com.nanyan.utils.OperationType;

import java.lang.annotation.*;


/**
 * 自定义注解类
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OptLog {
    OperationType operationType() default OperationType.OTHER;//操作类型
    String content() default "未知"; //操作内容
}
