package com.lhf.dajiuye.aspect;

//import com.lhf.dajiuye.api.bean.user.User;
//import com.lhf.dajiuye.api.service.user.MyUserService;
//import com.lhf.dajiuye.user.service.utils.EncryptUtil;
import com.lhf.dajiuye.service.user.MyUserService;
import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 对openId进行加密
 * Spring AOP就是基于动态代理的，如果要代理的对象，实现了某个接⼝，那么Spring AOP会使⽤JDK代理，去创建代理对象，
 * ⽽对于没有实现接⼝的对象，就⽆法使⽤ JDK代理去进⾏代理了，这时候Spring AOP会使⽤Cglib ，
 * 这时候Spring AOP会使⽤ Cglib代理 ⽣成⼀个被代理对象的⼦类来作为代理
 */
@Slf4j
//@Aspect
@Component
public class OpenIdAspect {

//    @Autowired
//    MyUserService userService;

//    @Pointcut(value = "@annotation(com.lhf.dajiuye.user.service.aspect.OpenIdHandle)")
//    public void PostSaveAspect(){
//
//    }
//
//    @After("PostSaveAspect()")  // After finally advice  所有发生异常也会执行
//    public void doAfter(){
//
//    }

    /**
     *
     @Override
     public Object invoke(MethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        // 这里目标方法已经执行完了，所以不能修改目标方法
        this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
        return retVal;
        应该只有around可以改变返回值
     }
     *
     */

    /**
     * 将com.lhf.dajiuye.controller.UserController.get*(..)下面的所有myEncrypt(即openId)进行解密
     * @param joinPoint
     * @return
     * @throws Throwable
     */
//    @Around(value = "PostSaveAspect()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("UserAspect环绕通知执行");
//        Object[] args = joinPoint.getArgs(); // 想要修改参数的话这里就可以修改
//        // 把所有的openId都解密
//        for (int i = 0; i < args.length; i++) {
//            if(args[i] instanceof String){
//                args[0]= EncryptUtil.decryptUtil((String) args[i]);
//            }
//        }
//        Object obj = joinPoint.proceed(args);  // 注意这里得传进去，不然修改不了入参
//        if(obj instanceof User){
//            System.out.println("环绕通知：返回值是User类型");
//            User ret=(User)obj;
//            User newUser = new User();
//            newUser.setResume(ret.getResume());
//            obj=newUser;
//        }
//        return obj;
//    }

//    @AfterReturning(returning = "ret",value = "PostSaveAspect()") // After returning advice ,argNames = "args"有什么用
    public void doAfterReturning(Map ret){
        // AfterReturning并不能改变返回值，因为目标方法已经执行
    }
}
