package com.demo.erpmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.erpmanage.common.DataGridView;
import com.demo.erpmanage.common.Tree;
import com.demo.erpmanage.entity.PageInfo;
import com.demo.erpmanage.entity.SysPermission;
import com.demo.erpmanage.entity.SysRole;
import com.demo.erpmanage.entity.SysUser;
import com.demo.erpmanage.service.SysPermissionService;
import com.demo.erpmanage.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/role")
public class SysRoleController {
    @Resource
    private SysRoleService roleService;
    @Resource
    private SysPermissionService permissionService;

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SysRole role) {
        Map<String, String> map = new HashMap<>();
        Integer result = roleService.modifyRole(role);
        if (result > 0) {
            map.put("code", "200");
            map.put("msg", "修改成功！");
        } else {
            map.put("code", "400");
            map.put("msg", "修改失败！");
        }
        return map;
    }

    @RequestMapping(value = "/savePermission")
    @ResponseBody
    public Object savePermission(Integer rid, Integer[] ids) {
        Map<String, String> map = new HashMap<>();
        boolean result = roleService.saveRolePermission(rid, ids);
        if (result) {
            map.put("msg", "分配成功!");
        } else {
            map.put("msg", "分配失败!");
        }
        return map;
    }

    @RequestMapping(value = "/initPermissionByRoleId")
    @ResponseBody
    public Object initPermissionByRoleId(Integer rid) {
        //查询全部权限
        List<SysPermission> permissionList = permissionService.findAllPermission();
        //通过角色id查询角色
        SysRole role = roleService.getById(rid);
        //查询当前角色的权限
        List<SysPermission> permissions = permissionService.findPermissionsByRole(role);
        List<Tree> treeList = new ArrayList<>();
        if (permissionList != null && permissionList.size() > 0) {
            for (SysPermission permission : permissionList) {
                String checkArr = "0";
                for (SysPermission permission2 : permissions) {
                    if (permission.getId() == permission2.getId()) {
                        checkArr = "1";
                        break;
                    }
                }
                treeList.add(new Tree(permission.getId(), permission.getPid(), permission.getTitle(), checkArr));
            }
        } else {
            treeList.add(new Tree(1, 0, "没有权限", "0"));
        }
        return new DataGridView(treeList.size(), treeList);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        boolean result = roleService.removeById(id);
        if (result) {
            map.put("code", "200");
            map.put("msg", "删除成功！");
        } else {
            map.put("code", "400");
            map.put("msg", "删除失败！");
        }
        return map;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SysRole role) {
        role.setCreatetime(new Date());
        Map<String, String> map = new HashMap<String, String>();
        boolean result = roleService.save(role);
        if (result) {
            map.put("code", "200");
            map.put("msg", "添加成功！");
        } else {
            map.put("code", "400");
            map.put("msg", "添加失败！");
        }
        return map;
    }

    @RequestMapping(value = "/getRoleByPage")
    @ResponseBody
    public Object getRoleByPage(SysRole role, PageInfo pageInfo) {
        IPage<SysRole> page = new Page<>(pageInfo.getPage(), pageInfo.getLimit());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(role.getName()), "name", role.getName());
        queryWrapper.like(StringUtils.isNotBlank(role.getRemark()), "remark", role.getRemark());
        queryWrapper.eq(role.getAvailable() != null, "available", role.getAvailable());
        roleService.page(page, queryWrapper);
        return new DataGridView((int) page.getTotal(), page.getRecords());
    }

    @RequestMapping(value = "/loadDeptManagerLeftTreeJson")
    @ResponseBody
    public Object loadDeptManagerLeftTreeJson(SysUser user) {
        //查询全部角色
        List<SysRole> list = roleService.list();
        //查询当前用户的角色
        List<SysRole> roles = roleService.findRoleByUser(user);

        //构造返回的数据格式
        List<Tree> treeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (SysRole role : list) {
                String checkArr = "0";
                for (SysRole role2 : roles) {
                    if (role.getId() == role2.getId()) {
                        checkArr = "1";
                        break;
                    }
                }
                treeList.add(new Tree(role.getId(), 0, role.getName(), checkArr));
            }
            return new DataGridView(treeList.size(), treeList);
        } else {
            treeList.add(new Tree(1, 0, "没有角色", "0"));
            return new DataGridView(1, treeList);
        }
    }
}

