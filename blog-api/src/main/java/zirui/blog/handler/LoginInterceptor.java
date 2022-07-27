package zirui.blog.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import zirui.blog.dao.pojo.Sys_user;
import zirui.blog.service.LoginService;
import zirui.blog.util.UserThreadLocal;
import zirui.blog.vo.ErrorCode;
import zirui.blog.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LoginInterceptor
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 22:22
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在执行controller方法(Handler)之前进行执行
        /**
         * 1. 需要判断 请求的接口路径是否为 HandlerMethod （controller方法）
         * 2. 判断 token是否为空，如果为空，未登录 跳转登录
         * 3. 验证token 登陆验证
         * 4. 成功即放行
         */
        if(!(handler instanceof HandlerMethod)) {
            // handler 可能是资源 RequestResourceHandler springboot程序访问静态资源默认去classpath下的static目录去查询
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if(StringUtils.isBlank(token)) {
            noLogin(response);
            return false;
        }
        Sys_user sysUser = loginService.checkToken(token);
        if(sysUser == null) {
            noLogin(response);
            return false;
        }
        // 将用户信息存入localThread保存
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 如果用完不删除localThread中的数据，有内存泄露的风险
        UserThreadLocal.remove();
    }

    private void noLogin(HttpServletResponse response) throws IOException {
        Result result = Result.fail(ErrorCode.NO_LOGIN);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSON.toJSONString(result));
    }
}
