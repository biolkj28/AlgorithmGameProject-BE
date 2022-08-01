package com.seventeam.algoritmgameproject.web.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class WebSocketAop {

//    @Around("execution(void com.seventeam.algoritmgameproject.web.socketServer.service..*.*(..))")
//    Object around(ProceedingJoinPoint joinPoint){
//        try {
//            joinPoint.proceed();
//        }catch (Exception e){
//            e.getMessage();
//        }
//        return
//    }
}
