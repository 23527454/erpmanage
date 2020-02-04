package com.demo.erpmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.erpmanage.common.DataGridView;
import com.demo.erpmanage.common.Tree;
import com.demo.erpmanage.entity.PageInfo;
import com.demo.erpmanage.entity.SysPermission;
import com.demo.erpmanage.service.SysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
@Controller
@RequestMapping("/Permission")
public class SysPermissionController {
    @Resource
    private SysPermissionService permissionService;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer id) {
        Map<String, String> map = new HashMap<>();
        boolean result = permissionService.removeById(id);
        if (result) {
            map.put("code", "200");
            map.put("msg", "删除成功！");
        } else {
            map.put("code", "400");
            map.put("msg", "删除失败！");
        }
        return map;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SysPermission permission) {
        Map<String, String> map = new HashMap<>();
        Integer result = permissionService.modifyPermission(permission);
        if (result > 0) {
            map.put("code", "200");
            map.put("msg", "修改成功！");
        } else {
            map.put("code", "400");
            map.put("msg", "修改失败！");
        }
        return map;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SysPermission permission) {
        permission.setType("permission");
        Map<String, String> map = new HashMap<>();
        boolean result = permissionService.save(permission);
        if (result) {
            map.put("code", "200");
            map.put("msg", "添加成功！");
        } else {
            map.put("code", "400");
            map.put("msg", "添加失败！");
        }
        return map;
    }

    @RequestMapping(value = "/loadPermissionMaxOrderNum")
    @ResponseBody
    public Object loadUserMaxOrderNum() {
        Map<String, Integer> map = new HashMap<>();
        Integer num = permissionService.loadPermissionMaxOrderNum() + 1;
        map.put("value", num);
        return map;
    }

    @RequestMapping(value = "/getPermissionAll")
    @ResponseBody
    public Object getPermissionAll(SysPermission permission, PageInfo pageInfo) {
        permission.setType("menu");
        Page<SysPermission> page = new Page<>(pageInfo.getPage(), pageInfo.getLimit());
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(permission.getId() != null, "pid", permission.getId());
        queryWrapper.like(StringUtils.isNotBlank(permission.getTitle()), "title", permission.getTitle());
        queryWrapper.eq(StringUtils.isNotBlank(permission.getPercode()), "percode", permission.getPercode());
        queryWrapper.ne("type", permission.getType());
        permissionService.page(page, queryWrapper);
        return new DataGridView((int) page.getTotal(), page.getRecords());
    }

    @RequestMapping(value = "/loadPermissionManagerLeftTreeJson")
    @ResponseBody
    public Object loadPermissionManagerLeftTreeJson(Integer rid) {
        //查询全部权限
        List<SysPermission> permissionList = permissionService.findAllPermissionMenu();
        List<Tree> treeList = new ArrayList<>();
        if (permissionList != null && permissionList.size() > 0) {
            for (SysPermission permission : permissionList) {
                String checkArr = "0";
                treeList.add(new Tree(permission.getId(), permission.getPid(), permission.getTitle(), checkArr));
            }
        } else {
            treeList.add(new Tree(1, 0, "没有权限", "0"));
        }
        return new DataGridView(treeList.size(), treeList);
    }
}

