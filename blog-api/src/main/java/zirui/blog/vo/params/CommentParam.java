package zirui.blog.vo.params;

import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CommentParam
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/26 16:12
 */
@Data
public class CommentParam {
    //@JsonSerialize(using = ToStringSerializer.class)
    private String articleId;
    //@JsonSerialize(using = ToStringSerializer.class)
    private String toUserId;
    //@JsonSerialize(using = ToStringSerializer.class)
    private String parent;
    private String content;
}
