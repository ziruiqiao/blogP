package zirui.blog.dao.dos;

import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: Archives
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 15:23
 */
@Data
public class Archives {
    private Integer year;
    private Integer month;
    private Long count;
}
