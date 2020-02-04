package com.demo.erpmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.erpmanage.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.erpmanage.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
public interface SysRoleService extends IService<SysRole> {

    public List<SysRole> findRoleByUser(SysUser user);

    public IPage<SysRole> findRoleByPage(IPage<SysRole> page, QueryWrapper<SysRole> queryWrapper);

    public boolean saveRolePermission(Integer rid, Integer[] ids);

    public Integer modifyRole(SysRole role);
}
