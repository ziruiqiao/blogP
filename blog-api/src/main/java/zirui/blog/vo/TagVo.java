package zirui.blog.vo;

import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: TagVo
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/23 17:59
 */
@Data
public class TagVo {
    //@JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String tagName;
}
