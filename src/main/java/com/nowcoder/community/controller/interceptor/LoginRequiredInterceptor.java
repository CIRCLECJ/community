package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {//Object handler是我们拦截的目标，这里表示只有拦截的是方法时才处理，因为我们还可能拦截到静态资源和其他，不需要所有都拦截
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);//取这个注解
            if (loginRequired != null && hostHolder.getUser() == null) {//不等于null表示我们在这个方法前加了注解，表示它是需要登录才能访问的
                response.sendRedirect(request.getContextPath() + "/login");
                //request.getContextPath()，可以通过配置文件定义然后注入一个参数进来，也可以这样从这个请求里直接去到页面的路径
                //因为它的返回值不是String，不能像controller直接返回模板页面重定向，
                // 因此需要用response.setRedirect,其实controllor里的本质也是这个
                return false;
            }
        }
        return true;
    }
}
