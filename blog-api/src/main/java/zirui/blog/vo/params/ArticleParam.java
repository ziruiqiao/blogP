package zirui.blog.vo.params;

import lombok.Data;
import zirui.blog.vo.CategoryVo;
import zirui.blog.vo.TagVo;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: ArticleParam
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/27 10:27
 */
@Data
public class ArticleParam {
    private String title;
    private String summary;
    //@JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private ArticleBodyParam body;
    private CategoryVo category;
    private List<TagVo> tags;

}
