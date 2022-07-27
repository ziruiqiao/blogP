package zirui.blog.service;

import zirui.blog.vo.CategoryVo;
import zirui.blog.vo.Result;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CategoryService
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 23:39
 */
public interface CategoryService {
    /**
     * @Description: 查询类别
     * @param categoryId
     * @return: java.util.List<zirui.blog.vo.CategoryVo>
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 23:41
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * @Description: 查询所有文章类别
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/27 9:58
     */
    Result findAll();
}
