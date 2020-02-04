package com.demo.erpmanage.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.erpmanage.entity.SysPermission;
import com.demo.erpmanage.entity.SysRole;
import com.demo.erpmanage.entity.SysUser;
import com.demo.erpmanage.service.SysPermissionService;
import com.demo.erpmanage.service.SysRoleService;
import com.demo.erpmanage.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private SysUserService userService;
    @Resource
    private SysRoleService roleService;
    @Resource
    private SysPermissionService permissionService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //用户登录次数计数  redisKey 前缀
    private String SHIRO_LOGIN_COUNT = "shiro_login_count_";

    //用户登录是否被锁定    一小时 redisKey 前缀
    private String SHIRO_IS_LOCK = "shiro_is_lock_";


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("1：身份认证-->MyShiroRealm.doGetAuthorizationInfo()");
        //获取用户输入的信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String loginName = token.getUsername();
        String pwd = new String(token.getPassword());

        //访问一次，计数一次
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.increment(SHIRO_LOGIN_COUNT + loginName, 1);  //每次增加1
        System.out.println(loginName + "：账号登陆的次数是：" + opsForValue.get(SHIRO_LOGIN_COUNT + loginName));

        //如果这个账号登陆异常，则在登陆页面提醒。
        if (Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT + loginName)) >= 3) {
            if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK + loginName))) {
                //计数大于3次，设置用户被锁定一分钟
                throw new DisabledAccountException("由于输入错误次数大于3次，帐号1分钟内已经禁止登录！");
            }
        }
        //实现锁定
        if (Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT + loginName)) >= 3) {
            opsForValue.set(SHIRO_IS_LOCK + loginName, "LOCK");  //锁住这个账号，值是LOCK。
            stringRedisTemplate.expire(SHIRO_IS_LOCK + loginName, 1, TimeUnit.MINUTES);  //expire  变量存活期限
        }

        //根据输入的信息去登录
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("loginname", loginName);
        queryWrapper.eq("pwd", pwd);
        SysUser loginUser = userService.login(queryWrapper);
        if (loginUser == null) {
            throw new UnknownAccountException("登录失败！");
        }

        List<SysRole> roles = roleService.findRoleByUser(loginUser);
        //角色不为空时获取权限
        if (roles != null && roles.size() > 0) {
            for (int i = 0; i < roles.size(); i++) {
                //获取权限
                List<SysPermission> permissions = permissionService.findPermissionsByRole(roles.get(i));
                //将权限存入角色中
                roles.get(i).getPermissionList().addAll(permissions);
            }
            //将角色存入用户中
            loginUser.setRoleList(roles);
        }
        //存入session中
        SecurityUtils.getSubject().getSession().setAttribute("user", loginUser);

        //认证信息
        SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(new SysUser(loginName, pwd), loginUser.getPwd(), loginUser.getLoginname());

        //清空登录计数
        opsForValue.set(SHIRO_LOGIN_COUNT + loginName, "0");
        //清空锁
        opsForValue.set(SHIRO_IS_LOCK + loginName, "");
        return authorizationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("2：权限认证-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser loginUser = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("user");

        //指定可以访问什么
        for (SysRole role : loginUser.getRoleList()) {
            for (SysPermission permission : role.getPermissionList()) {
                authorizationInfo.addStringPermission(permission.getTitle());
            }
        }
        return authorizationInfo;
    }
}
