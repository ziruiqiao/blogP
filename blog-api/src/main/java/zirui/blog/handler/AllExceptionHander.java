package zirui.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import zirui.blog.vo.Result;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: AllExceptionHander
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 14:22
 */
// 对加了@Controller注解的方法进行拦截处理 AOP实现
@ControllerAdvice
public class AllExceptionHander {
    // 进行异常处理，处理Exception.class 的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody // 返回json数据
    public Result doException(Exception e) {
        e.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}
