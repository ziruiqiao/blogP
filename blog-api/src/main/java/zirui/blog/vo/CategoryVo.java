package zirui.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CategoryVo
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 23:25
 */
@Data
public class CategoryVo {

    //@JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String avatar;
    private String categoryName;
    private String description;
}
