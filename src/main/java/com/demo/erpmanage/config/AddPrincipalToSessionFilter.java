package com.demo.erpmanage.config;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.erpmanage.entity.SysPermission;
import com.demo.erpmanage.entity.SysRole;
import com.demo.erpmanage.entity.SysUser;
import com.demo.erpmanage.service.SysPermissionService;
import com.demo.erpmanage.service.SysRoleService;
import com.demo.erpmanage.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 解决session丢失
 */
public class AddPrincipalToSessionFilter extends OncePerRequestFilter {
    @Autowired
    SysUserService userService;
    @Resource
    private SysRoleService roleService;
    @Resource
    private SysPermissionService permissionService;

    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("记住我登录——>doFilterInternal()");
        //查询当前用户的信息
        Subject subject = SecurityUtils.getSubject();
        //判断用户是不是通过自动登录进来的
        if (subject.isRemembered()) {
            //获取自动登录的用户
            SysUser user = (SysUser) subject.getPrincipal();
            System.out.println(user.getRoleList());
            if (user == null) {
                return;
            }
            //根据自动登录的用户信息查询该用户的信息是否匹配
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
            queryWrapper.eq("loginname", user.getLoginname());
            queryWrapper.eq("pwd", user.getPwd());
            SysUser loginUser = userService.login(queryWrapper);
            if (loginUser == null) {
                throw new UnknownAccountException("登录失败！");
            }
            //登录成功后获取该用户的角色
            List<SysRole> roles = roleService.findRoleByUser(loginUser);
            //角色不为空时获取权限
            if (roles != null && roles.size() > 0) {
                for (int i = 0; i < roles.size(); i++) {
                    //获取权限
                    List<SysPermission> permissions = permissionService.findPermissionsByRole(roles.get(i));
                    //将权限存入角色中
                    roles.get(i).getPermissionList().addAll(permissions);
                }
            }
            //将角色存入用户中
            loginUser.setRoleList(roles);
            //由于是继承的OncePerRequestFilter，没办法设置session
            //这里发现可以将servletReques强转为子类，所以使用request.getsiion())
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpSession session = request.getSession();
            //当session为空的时候
            if (session.getAttribute("user") == null) {
                //把查询到的用户信息设置为session，时效为3600秒
                session.setAttribute("user", loginUser);
                session.setMaxInactiveInterval(3600);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}