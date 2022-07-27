package zirui.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zirui.blog.service.ISys_userService;
import zirui.blog.vo.Result;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: UsersController
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 19:23
 */
@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private ISys_userService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);
    }
}
