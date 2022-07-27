package zirui.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String content;
    private String contentHtml;
}
