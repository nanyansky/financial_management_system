package com.nanyan.utils;

/**
 * @author nanyan
 * @version 1.0
 * @description: 用户注册通知
 * @date 2023/4/7 16:19
 */

import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static javax.mail.Message.RecipientType.TO;

public class MailUtil  {


    public static void sendMail(String email, String username) throws MessagingException {
        //创建配置文件
        Properties props = new Properties();
        //设置发送时遵从SMTP协议
        props.setProperty("mail.transport.protocol", "SMTP");
        /*
         * 发送邮件的域名
         * smtp.xx.com
         * smtp.qq.com则代表发送邮件时使用的邮箱域名来自qq
         * smtp.163.com则代表发送邮件时使用的邮箱域名来自163
         */
        props.setProperty("mail.host", "smtp.qq.com");
        //设置用户的认证方式auth
        props.setProperty("mail.smtp.auth", "true");
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //return new PasswordAuthentication("用户名", "密码");
                //注意qq邮箱需要去qq邮箱的设置中获取授权码，并将授权码作为密码来填写
                return new PasswordAuthentication("2421417099@qq.com", "nxdhsvhujebadjgd");
            }
        };
        //创建session域
        Session session = Session.getInstance(props, auth);
        Message message = new MimeMessage(session);
        //设置邮件发送者,与PasswordAuthentication中的邮箱一致即可
        message.setFrom(new InternetAddress("2421417099@qq.com"));
        //设置邮件主题
        message.setSubject("用户注册提醒");
        //设置邮件内容
        String content = "<html><head></head><body><h1>有新用户注册账户，用户名为："+ username +" ,请尽快登录系统审核！</h1></body></html>";
        message.setContent(content, "text/html;charset=utf-8");
        //
        // Address[] tos = new InternetAddress[email.size()];
        // for (int i = 0;i < email.size();i++){
        //     tos[i] = new InternetAddress(email.get(i));
        // }
        // for (int i = 0; i < tos.length; i++) {
        //     System.out.printf(String.valueOf(tos[i]));
        // }

        message.addRecipient(TO,new InternetAddress(email));
        //发送邮件
        Transport.send(message);
        // System.out.println("邮件发送成功！");
    }
}