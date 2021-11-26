package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CookieUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    //controller之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");

        if (ticket != null) {
            // 查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                // 根据凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                // 在本次请求中持有用户
                // （要考虑多线程环境，不能直接把user存在一个变量里，我们想把user存到一个地方让多线程并发访问都没有问题，
                // 就要考虑线程的隔离，如果每个线程存一份它们之间不互相干扰，
                // java多线程有一个工具ThreadLocal专门用于解决这方面的问题，把user存到ThreadLocal里，
                // 在多线程之间隔离存这个对象）
                hostHolder.setUser(user);
                //为什么可以持有这个用户，因为我们是把数据存在了当前线程的map里（threadlocal源码里实现的）
                // 只要这个请求没有处理完这个线程就一直还在，当请求处理完，服务器向浏览器做出响应之后，这个线程被销毁
            }
        }
        return true;//返回false程序不执行，一般都用true
    }

//在分布式的环境下，使用session存在共享数据的问题。通常的解决方案，是将共享数据存入数据库，所有的应用服务器都去数据库获取共享数据。对于每一次请求，开始时从数据库里取到数据，然后将其临时存放在本地的内存里，考虑到线程之间的隔离，所以用threadlocal，这样在本次请求的过程中，就可以随时获取到这份共享数据了。所以，session的替代方案是数据库，ThreadLocal只是打了个辅助。以上的内容，我在课上也讲啦，你不要追求速度，要注重听课的质量呀。
//关于会话，一般是采用cookie和session，而session分布式部署时需要解决共享问题，一般就采用db来替代它。现在我们用的是mysql，将来会换成redis。我们在会话里存的是用户的登录凭证，而不是用户信息本身。我们可以通过***，在请求开始将凭证置换成用户信息。为了便于在本次请求的后续部分获取到登录用户，所以我通过ThreadLocal，将其与当前线程绑定。那么问题来了，request对象就是本次请求，我们可以在请求结束前的任意时刻，从request对象里获取与本次请求有关的数据。实际上，确实可以通过request来持有用户数据的。但是从设计上、代码分层上来说，这样不好。在Spring MVC框架中，request是一个比较底层的数据对象，一般我们不直接使用它，看看你处理请求的代码就知道了。而且并不是任意的位置都方便获取request对象，因为它不被容器管理，不是随便就能注入给一个Bean的。因为不鼓励这样做，requerst是表现层的对象，要是随便注入，你很可能会将其注入给service，从而产生耦合。所以，我们自己写一个组件，单独解决这个问题。原则上可以，但事实上不会，这是从代码合理性角度考量的。

    //在模板引擎之前将这个user传入model里
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser", user);
        }
    }

    //在调模板引擎之后把数据清理
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
