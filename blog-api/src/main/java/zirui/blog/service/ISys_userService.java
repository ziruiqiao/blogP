package zirui.blog.service;

import zirui.blog.dao.pojo.Sys_user;
import com.baomidou.mybatisplus.extension.service.IService;
import zirui.blog.vo.LoginUserVo;
import zirui.blog.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
public interface ISys_userService extends IService<Sys_user> {
    Sys_user findUserById(Long id);

    /**
     * @Description: 根据用户名 密码查找用户
     * @param account 用户名
     * @param password 密码
     * @return: zirui.blog.dao.pojo.Sys_user
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 17:39
     */
    Sys_user findUser(String account, String password);

    /**
     * @Description: 根据token获取用户
     * @param token token
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 19:26
     */
    Result findUserByToken(String token);

    /**
     * @Description: 根据账号查找用户
     * @param account
     * @return: zirui.blog.dao.pojo.Sys_user
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 21:38
     */
    Sys_user findUserByAccount(String account);

    /**
     * @Description: 返回LoginUserVo对象
     * @param toUid
     * @return: zirui.blog.vo.LoginUserVo
     * @Author: Zirui Qiao
     * @Date: 2022/7/26 14:23
     */
    LoginUserVo findLoginUserVoById(Long toUid);
}
