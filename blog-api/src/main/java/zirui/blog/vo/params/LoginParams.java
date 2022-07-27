package zirui.blog.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LoginParams
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 17:05
 */
@Data
public class LoginParams {
    private String account;
    private String password;
    private String nickname;
}
