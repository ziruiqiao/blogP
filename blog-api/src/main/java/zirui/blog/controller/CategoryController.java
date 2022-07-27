package zirui.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zirui.blog.service.CategoryService;
import zirui.blog.vo.Result;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CategoryController
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/27 9:56
 */
@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categories() {
        return categoryService.findAll();
    }
}
