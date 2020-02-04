package com.demo.erpmanage.controller;

import com.demo.erpmanage.entity.SysUser;
import com.demo.erpmanage.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//import org.apache.shiro.authc.UsernamePasswordToken;

@RequestMapping(value = "/sys")
@Controller
public class SysController {
    @Resource
    private SysUserService sysUserService;

    @RequestMapping(value = "/toDeptRight")
    public String toDeptRight() {
        return "/system/dept/deptRight";
    }

    @RequestMapping(value = "/toDeptLeft")
    public String toDeptLeft() {
        return "/system/dept/deptLeft";
    }

    @RequestMapping(value = "/toDeptManager")
    public String toDeptManager() {
        return "/system/dept/deptManager";
    }

    @RequestMapping(value = "/toMenuRight")
    public String toMenuRight() {
        return "/system/menu/MenuRight";
    }

    @RequestMapping(value = "/toMenuLeft")
    public String toMenuLeft() {
        return "/system/menu/MenuLeft";
    }

    @RequestMapping(value = "/toMenuManager")
    public String toMenuManager() {
        return "/system/menu/MenuManager";
    }

    @RequestMapping(value = "/toPermissionManager")
    public String toPermissionManager() {
        return "/system/perission/permissionManager";
    }

    @RequestMapping(value = "/toPermissionRight")
    public String toPermissionRight() {
        return "/system/perission/permissionRight";
    }

    @RequestMapping(value = "/toPermissionLeft")
    public String toPermissionLeft() {
        return "/system/perission/permissionLeft";
    }

    @RequestMapping(value = "/toRoleManager")
    public String toRoleManager() {
        return "system/role/roleManagers";
    }

    @RequestMapping(value = "/toUserLeft")
    public String toUserLeft() {
        return "system/user/userLeft";
    }

    @RequestMapping(value = "/toUserRight")
    public String toUserRight() {
        return "system/user/userRight";
    }

    @RequestMapping(value = "/toUserManager")
    public String toUserManager() {
        return "system/user/userManager";
    }

    @RequestMapping(value = "/todeskManager")
    public String todeskManager() {
        return "system/index/deskManager";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "system/index/index";
    }

    @RequestMapping(value = "loginOut")
    public String loginOut(HttpSession session) {
        session.removeAttribute("user");
        return "system/index/login";
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public Object login(SysUser user, boolean rememberMe, HttpSession session) {
        Md5Hash md5Hash = new Md5Hash(user.getPwd());
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginname(), md5Hash.toString());
        token.setRememberMe(rememberMe);
        Map<String, String> map = new HashMap<String, String>();
        try {
            SecurityUtils.getSubject().login(token);//调用Shiro认证
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            map.put("msg", "登录成功！");
            map.put("code", "200");
        } catch (UnknownAccountException uae) {
            map.put("msg", "登录失败请检查用户名或密码是否正确！");
            map.put("code", "400");
        } catch (DisabledAccountException de) {
            map.put("msg", de.getMessage());
            map.put("code", "400");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "系统错误，请联系管理员！");
            map.put("code", "400");
        }
        return map;
    }

    @RequestMapping(value = "/toLogin")
    public String toLogin() {
        return "system/index/login";
    }
}
