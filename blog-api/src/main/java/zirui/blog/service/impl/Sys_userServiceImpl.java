package zirui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import zirui.blog.dao.pojo.Sys_user;
import zirui.blog.dao.mapper.Sys_userMapper;
import zirui.blog.service.ISys_userService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zirui.blog.service.LoginService;
import zirui.blog.util.CopyUtil;
import zirui.blog.vo.ErrorCode;
import zirui.blog.vo.LoginUserVo;
import zirui.blog.vo.Result;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
@Service
public class Sys_userServiceImpl extends ServiceImpl<Sys_userMapper, Sys_user> implements ISys_userService {

    @Autowired
    private Sys_userMapper sysUserMapper;
    @Autowired
    private LoginService loginService;


    @Override
    public Sys_user findUserById(String id) {
        Sys_user sys_user = sysUserMapper.selectById(id);
        if(sys_user == null) {
            sys_user = new Sys_user();
            sys_user.setNickname("默认名称");
        }

        return sys_user;
    }

    @Override
    public Sys_user findUser(String account, String password) {
        LambdaQueryWrapper<Sys_user> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Sys_user::getAccount, account)
                .eq(Sys_user::getPassword, password)
                .select(Sys_user::getAccount, Sys_user::getId, Sys_user::getAvatar, Sys_user::getNickname)
                .last("limit 1");
        Sys_user sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public Result findUserByToken(String token) {
        // 1. token合法性校验
        //  1.1 是否为空
        //  1.2 解析是否成功
        //  1.3 redis是否存在
        // 2. 如果校验失败，返回错误
        // 3. 如果成功， 返回结果 LoginUserVo
        Sys_user sysUser = loginService.checkToken(token);
        if(sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR);
        }
        LoginUserVo loginUserVo = CopyUtil.copyBean(sysUser, LoginUserVo.class);
        return Result.success(loginUserVo);
    }

    @Override
    public Sys_user findUserByAccount(String account) {
        LambdaQueryWrapper<Sys_user> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Sys_user::getAccount, account)
                .last("limit 1");
        Sys_user sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public LoginUserVo findLoginUserVoById(String id) {
        Sys_user sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new Sys_user();
            sysUser.setId("1");
            sysUser.setAvatar("/static/user/user_2.png");
            sysUser.setNickname("zirui-blog");
        }
        LoginUserVo userVo = new LoginUserVo();
        userVo.setAvatar(sysUser.getAvatar());
        userVo.setNickname(sysUser.getNickname());
        userVo.setId(sysUser.getId());
        return userVo;
    }
}
