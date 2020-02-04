package com.demo.erpmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.erpmanage.entity.SysPermission;
import com.demo.erpmanage.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
public interface SysPermissionService extends IService<SysPermission> {
    public List<SysPermission> findPermissionsByRole(SysRole sysRole);

    public List<SysPermission> findAllPermission();

    public List<SysPermission> findAllPermissionMenu();

    public Integer loadPermissionMaxOrderNum();

    public Integer modifyPermission(SysPermission permission);

    public Integer examinemenuSubdivision(Integer id);

    public Integer modifyPermissionMenu(SysPermission permission);
}
