package com.nowcoder.community.controller.advice;

import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice(annotations = Controller.class)//不写就扫描所有的bean,写上之后这个注解就只扫描带有controller注解的bean
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);//日志主件实例化

    @ExceptionHandler({Exception.class})//表示所有异常都用它来处理（常用的参数就这三个）
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常: " + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {//把异常的栈的信息全部记录下来
            logger.error(element.toString());
        }

        //判断是普通请求还是异步请求，普通直接返回错误页面，异步返回json字符串（！经验 技巧）
        String xRequestedWith = request.getHeader("x-requested-with");//获取到 请求的方式
        if ("XMLHttpRequest".equals(xRequestedWith)) {//这个key的value是XMLHttpRequest，表示它是异步请求
            response.setContentType("application/plain;charset=utf-8");//表示我们向浏览器返回一个普通的字符串，并声明字符去utf8
            PrintWriter writer = response.getWriter();//获取输出流
            writer.write(CommunityUtil.getJSONString(1, "服务器异常!"));
        } else {
            response.sendRedirect(request.getContextPath() + "/error");//普通请求就重定向到error页面
        }
    }

}