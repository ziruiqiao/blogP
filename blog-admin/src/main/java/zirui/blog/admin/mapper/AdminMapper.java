package zirui.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import zirui.blog.admin.pojo.Admin;
import zirui.blog.admin.pojo.Permission;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: AdminMapper
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/28 18:25
 */
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from db_permission where id in " +
            "(select permission_id from db_admin_permission where admin_id=#{id})")
    List<Permission> findPermissionsByAdminId(Long id);
}
