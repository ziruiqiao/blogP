package zirui.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zirui.blog.service.LoginService;
import zirui.blog.vo.Result;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LogoutController
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 20:19
 */
@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
