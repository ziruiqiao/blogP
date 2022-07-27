package zirui.blog.util;

import zirui.blog.dao.pojo.Sys_user;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: UserThreadLocal
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 22:49
 */
public class UserThreadLocal {
    private UserThreadLocal(){}

    private static final ThreadLocal<Sys_user> LOCAL = new ThreadLocal<>();

    public static void put(Sys_user sysUser) {
        LOCAL.set(sysUser);
    }
    public static Sys_user get() {
        return LOCAL.get();
    }
    public static void remove() {
        LOCAL.remove();
    }
}
