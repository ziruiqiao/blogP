package zirui.blog.service;

import org.springframework.transaction.annotation.Transactional;
import zirui.blog.dao.pojo.Sys_user;
import zirui.blog.vo.Result;
import zirui.blog.vo.params.LoginParams;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LoginServier
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 17:02
 */
@Transactional
public interface LoginService {
    /**
     * 登录
     * @param loginParam
     * @return
     */
    Result login(LoginParams loginParam);

    /**
     * @Description: 校验token 如果成功返回对应的用户
     * @param token token
     * @return: zirui.blog.dao.pojo.Sys_user
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 19:43
     */
    Sys_user checkToken(String token);

    /**
     * @Description: 用户登出
     * @param token token
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 20:22
     */
    Result logout(String token);

    /**
     * @Description: 注册
     * @param loginParams
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 21:16
     */
    Result register(LoginParams loginParams);
}
