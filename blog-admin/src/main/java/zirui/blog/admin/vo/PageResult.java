package zirui.blog.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: PageResult
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/28 17:27
 */
@Data
public class PageResult<T> {
    private List<T> list;

    private Long total;
}
