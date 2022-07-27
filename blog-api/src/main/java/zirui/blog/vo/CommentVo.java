package zirui.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;
/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CommentVo
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/26 13:39
 */
@Data
public class CommentVo  {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private LoginUserVo author;

    private String content;

    private List<CommentVo> children;

    private String createDate;

    private Integer level;

    private LoginUserVo toUser;
}

