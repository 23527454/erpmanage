package com.demo.erpmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.erpmanage.entity.SysDept;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
public interface SysDeptService extends IService<SysDept> {
    public List<SysDept> findAllDept();

    public Integer loadDeptMaxOrderNum();

    public Integer modifyDept(SysDept dept);

    public Integer examineDeptSubdivision(Integer id);
}
