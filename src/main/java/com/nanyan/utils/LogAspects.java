package com.nanyan.utils;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.entity.OperationLog;
import com.nanyan.service.OperationLogService;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.sql.Timestamp;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/1 11:20
 */

@Aspect
@Component
public class LogAspects {


    @Autowired
    OperationLogService operationLogService;

    // 设置操作日志切入点
    @Pointcut(value = "@annotation(com.nanyan.annotation.OptLog)")
    public void optLogPointCut(){}


    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @AfterReturning(value = "optLogPointCut()", returning = "keys")
    public void saveOptLog(JoinPoint joinPoint, Object keys) {

        HttpSession session = ServletActionContext.getRequest().getSession();
        //获取用户名
        String username = (String) session.getAttribute("username");

        OperationLog operationLog = new OperationLog();
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        OptLog optLog = method.getAnnotation(OptLog.class);

        // 操作用户
        operationLog.setUserName(username);
        System.out.println("操作的用户："+ username);
        // 操作类型
        operationLog.setOperationType(optLog.operationType());
        System.out.println("操作的类型："+ optLog.operationType());

        operationLog.setOperationTime(new Timestamp(System.currentTimeMillis()));

        operationLog.setOperationContent(optLog.describe());
        System.out.println("操作的内容："+ optLog.describe());

        operationLogService.addOperationLog(operationLog);
    }
}
