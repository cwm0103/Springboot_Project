package com.sbm.aspects;

import com.sbm.util.IPUtil;
import org.apache.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * sbm
 * Created by yadong.zhang on com.sbm.aspects
 * User：yadong.zhang
 * Date：2016/10/21
 * Time：15:53
 */
@Component
@Aspect

/*
 * 定义切面执行的优先级，数字越低，优先级越高
 * 在切入点之前执行：按order值有小到大的顺序执行
 * 在切入点之后执行：按order值由大到小的顺序执行
 */
@Order(-5)
public class AppLogAspect {

    private org.apache.log4j.Logger logger = LogManager.getLogger(AppLogAspect.class);

    // 保证每个线程都有一个单独的实例
    private ThreadLocal<Long> time = new ThreadLocal<>();

    @Pointcut("execution(* com.sbm.controller..*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        time.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //记录请求的内容
        logger.info("Request URL: " + request.getRequestURL().toString());
        logger.info("Request Method: " + request.getMethod());

        String ip = request.getRemoteAddr();
        if (ip.indexOf(":0") > -1) {
            ip = IPUtil.getRealIp();
        }
        logger.info("IP: " + ip);
        logger.info("User-Agent: " + request.getHeader("User-Agent"));
        logger.info("Class Method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("Cookies: " + request.getCookies());

        logger.info("Params: " + Arrays.toString(joinPoint.getArgs()));

        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paraName = enums.nextElement();
            logger.info(paraName + ":" + request.getParameter(paraName));
        }
    }

    @AfterReturning("pointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        logger.info("耗时 : " + ((System.currentTimeMillis() - time.get())) + "ms");
        logger.info("AppLogAspect.doAfterReturning()");
    }


}
