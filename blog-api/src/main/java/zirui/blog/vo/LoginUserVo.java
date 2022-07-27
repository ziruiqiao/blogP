package zirui.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LoginUserVo
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 19:58
 */
@Data
public class LoginUserVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String account;
    private String nickname;
    private String avatar;
}
