package zirui.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: Result
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/23 17:13
 */
@Data
@AllArgsConstructor
public class Result {

    private Boolean success;
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }

    public static Result fail(ErrorCode errorCode) {
        return new Result(false, errorCode.getCode(), errorCode.getMsg(), null);
    }
}
