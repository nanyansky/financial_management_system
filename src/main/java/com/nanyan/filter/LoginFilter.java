package com.nanyan.filter;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/3/21 15:16
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        int flag = 1;
        //获取本次请求URI
        String path = request.getServletPath();
        System.out.println("拦截到请求: "+ path);

        //不需要拦截的请求
        String[] urls = {
                "/login.jsp",
                "/register.jsp",
                "/logout",
                "statics",
                ".ico"
        };
        for (String str : urls) {
            if (path.contains(str)) {
                flag = 0;
                System.out.println(path+"不需要拦截！");
                break;
            }
        }

        if(flag == 1){
            if(request.getSession().getAttribute("user") == null){
                System.out.println("未登录，跳转至登录页面！");
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
            else {
                filterChain.doFilter(request,response);
            }
        }
        else {
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
