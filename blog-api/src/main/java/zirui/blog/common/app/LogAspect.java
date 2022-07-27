package zirui.blog.common.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import zirui.blog.util.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LogAspect
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/27 13:30
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(zirui.blog.common.app.LogAnnotation)")
    public void pt() {

    }

    private static String[] types = {"java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float"};

    @Around("pt()")
    public Object log(ProceedingJoinPoint point) throws Throwable {

        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        recordLog(point, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        log.info("=====================log start================================");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("module:{}", logAnnotation.module());
        log.info("operation:{}", logAnnotation.operator());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");

//        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}", params);

        //获取request 设置IP地址
        log.info("ip:{}", IpUtils.getIpAddr());

        // 打印request header和body
        //printRequest(HttpContextUtils.getHttpServletRequest(), joinPoint);

        log.info("execute time : {} ms", time);
        log.info("=====================log end================================");
    }

    private void printRequest(HttpServletRequest request, ProceedingJoinPoint joinPoint) {
        Enumeration<String> headerNames = request.getHeaderNames();
        log.info("  header: {");
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            // 排除Cookie字段
            if (key.equalsIgnoreCase("Cookie")) {
                continue;
            }
            String value = request.getHeader(key);
            log.info("      key: {} value: {}", key, value);
        }
        log.info("} body: {");
        readBody(joinPoint);
        log.info("}");
    }


    public void readBody(JoinPoint joinPoint) {
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String logStr = "";
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();
            // 记录下请求内容
            logStr += "URL:" + request.getRequestURI().toString() + " | ";
            logStr += "CLASS_METHOD:" + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName() + " | ";
            logStr += "ARGS:";
            // joinPoint获取参数名
            String[] params = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterNames();
            // joinPoint获取参数值
            Object[] args = joinPoint.getArgs();
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            if (true) {
                // 打印请求参数
                int i = 0;
                for (Object arg : args) {
                    if (arg == request || arg == response) {
                        i += 1;
                        continue;
                    }
                    String typeName = "";
                    try {
                        typeName = arg.getClass().getTypeName();
                    } catch (Exception e) {
                    }
                    if (!Arrays.asList(types).contains(typeName)) {
                        // 把参数转成json格式
                        logStr += "&" + params[i] + "=" + JSONObject.toJSONString(arg);
                    } else {
                        logStr += "&" + params[i] + "=" + arg;
                    }
                    i += 1;
                }
            }
            logStr += " | ";
            log.info("print request: ");
            log.info(logStr);
        } catch (Throwable e) {
            log.error("HaLogParamAspect error.", e);
        }
    }

}
