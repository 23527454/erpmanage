package com.demo.erpmanage.service.impl;

import com.demo.erpmanage.entity.SysDept;
import com.demo.erpmanage.mapper.SysDeptMapper;
import com.demo.erpmanage.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Resource
    private SysDeptMapper deptMapper;

    @Override
    public List<SysDept> findAllDept() {
        return deptMapper.findAllDept();
    }

    @Override
    public Integer loadDeptMaxOrderNum() {
        return deptMapper.loadDeptMaxOrderNum();
    }

    @Override
    public Integer modifyDept(SysDept dept) {
        return deptMapper.modifyDept(dept.getPid(), dept.getTitle(), dept.getAddress(), dept.getRemark(), dept.getOpen(), dept.getAvailable(), dept.getOrdernum(), dept.getId());
    }

    @Override
    public Integer examineDeptSubdivision(Integer id) {
        return deptMapper.examineDeptSubdivision(id);
    }
}
