package zirui.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zirui.blog.common.app.LogAnnotation;
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
    @LogAnnotation(module="分类",operator="获取所有分类")
    public Result categories() {
        return categoryService.findAll();
    }

    @GetMapping("detail")
    @LogAnnotation(module="分类",operator="获取所有分类细节")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    @LogAnnotation(module="分类",operator="根据id获取分类细节")
    public Result categoryDetailById(@PathVariable("id") String id) {
        return categoryService.categoryDetailById(id);
    }
}
