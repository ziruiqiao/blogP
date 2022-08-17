package zirui.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: Category
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 23:17
 */
@Data
public class Category {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String avatar;
    private String categoryName;
    private String description;
}
