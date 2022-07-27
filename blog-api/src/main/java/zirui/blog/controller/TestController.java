package zirui.blog.controller;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: TestController
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 22:44
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zirui.blog.vo.Result;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        return Result.success(null);
    }
}