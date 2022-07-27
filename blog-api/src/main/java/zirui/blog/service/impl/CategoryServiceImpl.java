package zirui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zirui.blog.dao.mapper.CategoryMapper;
import zirui.blog.dao.pojo.Category;
import zirui.blog.service.CategoryService;
import zirui.blog.util.CopyUtil;
import zirui.blog.vo.CategoryVo;
import zirui.blog.vo.Result;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CategoryServiceImpl
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 23:39
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);

        return CopyUtil.copyBean(category, CategoryVo.class);
    }

    @Override
    public Result findAll() {
        List<Category> categories = categoryMapper.selectList(new QueryWrapper<Category>());
        //页面交互对象

        return Result.success(CopyUtil.copyList(categories, CategoryVo.class));
    }
}
