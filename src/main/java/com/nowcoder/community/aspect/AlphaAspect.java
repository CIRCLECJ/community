package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {

    //写一个表达式描述哪些方法哪些bean是我们要处理的目标；
    //*代表方法的返回值，表示什么返回值都行；
    // com.nowcoder.community.service是包名，然后.*表示这个包下所有的类（所有的业务组件），
    // 再.*表示所有的方法，（..）表示所有的参数
    //所以这个表达式就是这个包里所有的业务组件所有的类所有的方法所有的参数所有的返回值我们都要处理。当然写出明确的需要处理的部分也是可以的
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut() {

    }

    //advice
    @Before("pointcut()")//如果你想在这个连接到开始的地方记日志就用@Before，后面的参数pointcut()表示对这个切点（连接点）有效
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("pointcut()")//在有了返回值以后
    public void afterRetuning() {
        System.out.println("afterRetuning");
    }

    @AfterThrowing("pointcut()")//在抛异常之后
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around("pointcut()")//在切点前后都织入逻辑
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }
}
