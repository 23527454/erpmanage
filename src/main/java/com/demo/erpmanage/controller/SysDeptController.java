package com.demo.erpmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.erpmanage.common.DataGridView;
import com.demo.erpmanage.common.Tree;
import com.demo.erpmanage.entity.PageInfo;
import com.demo.erpmanage.entity.SysDept;
import com.demo.erpmanage.service.SysDeptService;
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
@RequestMapping("/Dept")
public class SysDeptController {
    @Resource
    private SysDeptService deptService;

    @RequestMapping(value = "/deletebyid")
    @ResponseBody
    public Object delete(Integer id) {
        Map<String, String> map = new HashMap<>();
        boolean result = deptService.removeById(id);
        if (result) {
            map.put("code", "200");
            map.put("msg", "删除成功！");
        } else {
            map.put("code", "400");
            map.put("msg", "删除失败！");
        }
        return map;
    }

    @RequestMapping(value = "/examineDeptSubdivision")
    @ResponseBody
    public Object examineDeptSubdivision(Integer id) {
        Map<String, Boolean> map = new HashMap<>();
        Integer result = deptService.examineDeptSubdivision(id);
        if (result > 0) {
            map.put("value", true);
        } else {
            map.put("value", false);
        }
        return map;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SysDept dept) {
        Map<String, String> map = new HashMap<>();
        Integer result = deptService.modifyDept(dept);
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
    public Object add(SysDept dept) {
        dept.setCreatetime(new Date());
        Map<String, String> map = new HashMap<>();
        boolean result = deptService.save(dept);
        if (result) {
            map.put("code", "200");
            map.put("msg", "添加成功！");
        } else {
            map.put("code", "400");
            map.put("msg", "添加失败！");
        }
        return map;
    }

    @RequestMapping(value = "/loadDeptMaxOrderNum")
    @ResponseBody
    public Object loadDeptMaxOrderNum() {
        Map<String, Integer> map = new HashMap<>();
        Integer num = deptService.loadDeptMaxOrderNum() + 1;
        map.put("value", num);
        return map;
    }

    @RequestMapping(value = "/getDeptAll")
    @ResponseBody
    public Object getDeptAll(SysDept dept, PageInfo pageInfo) {
        Page<SysDept> page = new Page<>(pageInfo.getPage(), pageInfo.getLimit());
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(dept.getId() != null, Wrapper -> Wrapper.eq(dept.getId() != null, "pid", dept.getId()).or().eq(dept.getId() != null, "id", dept.getId()));
        queryWrapper.like(StringUtils.isNotBlank(dept.getTitle()), "title", dept.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(dept.getRemark()), "remark", dept.getRemark());
        queryWrapper.like(StringUtils.isNotBlank(dept.getAddress()), "address", dept.getAddress());
        deptService.page(page, queryWrapper);
        return new DataGridView((int) page.getTotal(), page.getRecords());
    }

    @RequestMapping(value = "/loadDeptManagerLeftTreeJson")
    @ResponseBody
    public Object loadDeptManagerLeftTreeJson() {
        List<SysDept> deptList = deptService.findAllDept();
        List<Tree> treeList = new ArrayList<>();
        if (deptList != null && deptList.size() > 0) {
            for (SysDept dept : deptList) {
                String checkArr = "0";
                treeList.add(new Tree(dept.getId(), dept.getPid(), dept.getTitle(), checkArr));
            }
            return new DataGridView(treeList.size(), treeList);
        } else {
            treeList.add(new Tree(1, 0, "没有角色", "0"));
            return new DataGridView(1, treeList);
        }
    }
}

