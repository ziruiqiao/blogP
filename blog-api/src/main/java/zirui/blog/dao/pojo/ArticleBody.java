package zirui.blog.dao.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: ArticleBody
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 23:16
 */
@Data
public class ArticleBody {
    private Long id;
    private Long articleId;
    private String content;
    private String contentHtml;
}
