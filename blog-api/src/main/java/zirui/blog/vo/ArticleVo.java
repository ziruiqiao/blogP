package zirui.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: ArticleVo
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/23 17:59
 */
@Data
public class ArticleVo {

    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

//    private ArticleBodyVo body;

    private List<TagVo> tags;

//    private List<CategoryVo> categorys;

}
