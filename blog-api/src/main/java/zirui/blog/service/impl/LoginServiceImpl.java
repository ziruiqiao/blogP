package zirui.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zirui.blog.dao.pojo.Sys_user;
import zirui.blog.service.ISys_userService;
import zirui.blog.service.LoginService;
import zirui.blog.util.CopyUtil;
import zirui.blog.util.JWTUtils;
import zirui.blog.vo.ErrorCode;
import zirui.blog.vo.Result;
import zirui.blog.vo.params.LoginParams;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LoginServiceImpl
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 17:08
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    @Lazy
    private ISys_userService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String salt = "mszlu!@#";

    @Override
    public Result login(LoginParams loginParam) {
        // 1. 检查参数是否合法
        // 2. 根据用户名和密码去user表中查询是否存在
        // 3. 如果不存在，登陆失败
        // 4. 如果存在，使用jwt生成token，返回给前端
        // 5. token放入redis当中，redis token: user 信息 设置过期时间
        // （登录认证的时候，先认证token字符串是否合法，去redis认证是否存在）
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        password = DigestUtils.md5Hex(password + salt);
        Sys_user sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST);
        }
        String token = JWTUtils.createToken(Long.parseLong(sysUser.getId()));

        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    public Sys_user checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        Sys_user sysUser = JSON.parseObject(userJson, Sys_user.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        /**
         * 1. 判断参数 是否合法
         * 2. 判断账户是否存在，
         * 2.1 存在：返回“账户已经被注册”
         * 2.2 不存在：
         * 2.2.1 生成token
         * 2.2.2 存入redis 并返回
         * 3. 注意加上事务
         */
        if (CopyUtil.getNullPropertyNames(loginParams).length > 0) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        Sys_user sysUser = sysUserService.findUserByAccount(loginParams.getAccount());
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST);
        }
        sysUser = new Sys_user();
        sysUser.setNickname(loginParams.getNickname());
        sysUser.setAccount(loginParams.getAccount());
        sysUser.setPassword(DigestUtils.md5Hex(loginParams.getPassword() + salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/user/user_1.png");
        sysUser.setAdmin(true);
        sysUser.setDeleted(false);
        sysUser.setSalt("");
        sysUser.setEmail("");
        sysUser.setMobilePhoneNumber("");
        sysUser.setStatus("");
        sysUserService.save(sysUser);

        String token = JWTUtils.createToken(Long.parseLong(sysUser.getId()));

        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
