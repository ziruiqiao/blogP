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
 * @className: LoginController
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 16:58
 */
@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParams loginParams) {
        // 登录 验证用户 访问用户表 ，但是
        return loginService.login(loginParams);
    }
}
