package zirui.blog.dao.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: Comment
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/26 13:19
 */
@Data
public class Comment {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}