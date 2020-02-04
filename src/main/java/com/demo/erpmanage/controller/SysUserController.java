package com.demo.erpmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.erpmanage.common.DataGridView;
import com.demo.erpmanage.entity.PageInfo;
import com.demo.erpmanage.entity.SysDept;
import com.demo.erpmanage.entity.SysUser;
import com.demo.erpmanage.service.SysDeptService;
import com.demo.erpmanage.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
@Controller
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private SysUserService userService;
    @Resource
    private SysDeptService deptService;

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SysUser user) {
        Map<String, String> map = new HashMap<>();
        Integer result = 0;
        try {
            result = userService.modifyUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        if (result > 0) {
            map.put("code", "200");
            map.put("msg", "修改成功！");
        } else {
            map.put("code", "500");
            map.put("msg", "修改失败！");
        }
        return map;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SysUser user) {
        user.setPwd(new Md5Hash("123456").toString());
        user.setType(2);
        user.setHiredate(new Date());

        Map<String, String> map = new HashMap<>();
        boolean result = false;
        try {
            if (userService.findLoginNameCount(user.getLoginname())) {
                map.put("code", "500");
                map.put("msg", "登录名已存在！");
                return map;
            }
            result = userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result) {
            map.put("code", "200");
            map.put("msg", "添加成功！");
        } else {
            map.put("code", "500");
            map.put("msg", "添加失败！");
        }
        return map;
    }


    @RequestMapping(value = "/loadUserMaxOrderNum")
    @ResponseBody
    public Object loadUserMaxOrderNum() {
        Map<String, Integer> map = new HashMap<>();
        Integer num = userService.loadUserMaxOrderNum() + 1;
        map.put("value", num);
        return map;
    }

    @RequestMapping(value = "/loadUserbyId")
    @ResponseBody
    public Object loadUserbyId(Integer id) {
        Map<String, Object> map = new HashMap<>();
        SysUser user = userService.loadUserbyId(id);
        map.put("mgr", user);
        return map;
    }

    @RequestMapping(value = "/getUserallByMgrDeptId")
    @ResponseBody
    public Object getUserallByMgrDeptId(Integer id) {
        List<SysUser> userList = new ArrayList<>();
        userList = userService.findUserByDeptId(id);
        return new DataGridView(userList.size(), userList);
    }

    @RequestMapping(value = "/resetPwd")
    @ResponseBody
    public Object resetPwd(Integer id) {
        Map<String, String> map = new HashMap<>();
        Integer result = 0;
        String pwd = new Md5Hash("123456").toString();
        try {
            if (id != null) {
                result = userService.resetPwd(id, pwd);
            }
        } catch (Exception e) {
            result = -1;
        }
        if (result > 0) {
            map.put("code", "200");
            map.put("msg", "重置成功！");
        } else {
            map.put("code", "500");
            map.put("msg", "重置失败！");
        }
        return map;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer id) {
        Map<String, String> map = new HashMap<>();
        boolean result = false;
        try {
            if (id != null) {
                result = userService.removeById(id);
            }
        } catch (Exception e) {
            result = false;
        }
        if (result) {
            map.put("code", "200");
            map.put("msg", "删除成功！");
        } else {
            map.put("code", "500");
            map.put("msg", "删除失败！");
        }
        return map;
    }

    @RequestMapping(value = "/saveUserRole")
    @ResponseBody
    public Object saveUserRole(Integer uid, Integer[] ids) {
        Map<String, String> map = new HashMap<String, String>();
        boolean result = userService.modifyUserRole(uid, ids);
        if (result) {
            map.put("msg", "修改成功！");
        } else {
            map.put("msg", "修改失败！");
        }
        return map;
    }

    @RequestMapping(value = "/getUserAll")
    @ResponseBody
    public Object getUserAll(SysUser user, PageInfo pageInfo) {
        Page<SysUser> page = new Page<>(pageInfo.getPage(), pageInfo.getLimit());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(user.getName()), "name", user.getName());
        queryWrapper.like(StringUtils.isNotBlank(user.getAddress()), "address", user.getAddress());
        queryWrapper.eq(user.getDeptid() != null, "deptid", user.getDeptid());
        userService.page(page, queryWrapper);
        List<SysUser> users = page.getRecords();
        for (int i = 0; i < users.size(); i++) {
            SysDept dept = deptService.getById(users.get(i).getDeptid());
            if (dept != null) {
                users.get(i).setDept(dept);
                users.get(i).setDeptTitle(dept.getTitle());
            }
            SysUser user2 = userService.getById(users.get(i).getMgr());
            if (user2 != null) {
                users.get(i).setMgrName(user2.getName());
            }
        }
        return new DataGridView((int) page.getTotal(), users);
    }
}

