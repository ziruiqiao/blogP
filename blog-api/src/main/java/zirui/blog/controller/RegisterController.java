package zirui.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zirui.blog.service.LoginService;
import zirui.blog.vo.Result;
import zirui.blog.vo.params.LoginParams;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: RegisterController
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 21:12
 */
@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {
        // sso, 单点登录， 后期如果把登录注册功能提出去（单独的服务，可以独立提供）
        return loginService.register(loginParams);
    }
}
