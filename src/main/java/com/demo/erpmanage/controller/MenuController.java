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

@Controller
@RequestMapping(value = "/menu")
public class MenuController {
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

    @RequestMapping(value = "/examinemenuSubdivision")
    @ResponseBody
    public Object examinemenuSubdivision(Integer id) {
        Map<String, Boolean> map = new HashMap<>();
        Integer result = permissionService.examinemenuSubdivision(id);
        if (result > 0) {
            map.put("value", true);
        } else {
            map.put("value", false);
        }
        return map;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SysPermission permission) {
        Map<String, String> map = new HashMap<>();
        Integer result = permissionService.modifyPermissionMenu(permission);
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
        permission.setType("menu");
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

    @RequestMapping(value = "/loadmenuMaxOrderNum")
    @ResponseBody
    public Object loadmenuMaxOrderNum() {
        Map<String, Integer> map = new HashMap<>();
        Integer num = permissionService.loadPermissionMaxOrderNum() + 1;
        map.put("value", num);
        return map;
    }

    @RequestMapping(value = "/getmenuAll")
    @ResponseBody
    public Object getmenuAll(SysPermission permission, PageInfo pageInfo) {
        permission.setType("menu");
        Page<SysPermission> page = new Page<>(pageInfo.getPage(), pageInfo.getLimit());
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", permission.getType());
        queryWrapper.and(permission.getId() != null, Wrapper -> Wrapper.eq(permission.getId() != null, "pid", permission.getId()).or().eq(permission.getId() != null, "id", permission.getId()));
        queryWrapper.like(StringUtils.isNotBlank(permission.getTitle()), "title", permission.getTitle());
        permissionService.page(page, queryWrapper);
        return new DataGridView((int) page.getTotal(), page.getRecords());
    }

    @RequestMapping(value = "/loadmenuManagerLeftTreeJson")
    @ResponseBody
    public Object loadmenuManagerLeftTreeJson() {
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
