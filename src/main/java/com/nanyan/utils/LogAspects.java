package com.nanyan.utils;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.entity.OperationLog;
import com.nanyan.entity.User;
import com.nanyan.service.OperationLogService;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    @Pointcut("@annotation(com.nanyan.annotation.OptLog)")
    public void optLogPointCut(){}

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @AfterReturning(value = "optLogPointCut() && @annotation(optLog)", returning = "keys")
    public void saveOptLog(JoinPoint joinPoint, Object keys,OptLog optLog) {

        HttpSession session = ServletActionContext.getRequest().getSession();

        OperationLog operationLog = new OperationLog();
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();

        //获取参数
        Object[] args = joinPoint.getArgs();
        Object[] arguments  = new Object[args.length];

        //        获取用户名
        User tmpUser = (User) session.getAttribute("user");
        String username = "";
        if(tmpUser != null){
            username = tmpUser.getUserName();
        }
        else {
            //用户注册时，session中无user，所以需要单独传参数
            username = (String) args[0];
        }

        // 操作用户
        operationLog.setOperationTime(new Timestamp(System.currentTimeMillis()));
        operationLog.setUserName(username);
        System.out.println("操作的用户："+ username);
        //   操作类型
        operationLog.setOperationType(optLog.operationType());
        System.out.println("操作的类型："+ optLog.operationType());


        operationLog.setOperationContent(optLog.content());
        System.out.println("操作的内容："+ optLog.content());

        System.out.println(operationLog);
        operationLogService.addOperationLog(operationLog);
    }
}
