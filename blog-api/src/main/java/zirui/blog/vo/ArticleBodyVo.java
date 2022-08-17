package zirui.blog.vo;

import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: ArticleBodyVo
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 23:24
 */
@Data
public class ArticleBodyVo {
    //@JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String content;
    private String contentHtml;
}
