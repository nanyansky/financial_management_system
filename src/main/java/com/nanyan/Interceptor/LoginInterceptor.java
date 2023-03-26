package com.nanyan.Interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

/**
 * @author nanyan
 * @version 1.0
 * @description: 登录拦截器
 * @date 2023/3/21 13:18
 */
public class LoginInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        System.out.println("LoginInterceptor");
        HttpSession session = ServletActionContext.getRequest().getSession();
        if(session.getAttribute("user") == null){
            return Action.LOGIN;
        }
        else return actionInvocation.invoke();
    }
}
