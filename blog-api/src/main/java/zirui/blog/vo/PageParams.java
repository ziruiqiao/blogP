package zirui.blog.vo;

import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: PageParams
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/23 17:12
 */
@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;
}
