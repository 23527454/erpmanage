package com.demo.erpmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.erpmanage.entity.SysPermission;
import com.demo.erpmanage.entity.SysRole;
import com.demo.erpmanage.mapper.SysPermissionMapper;
import com.demo.erpmanage.service.SysPermissionService;
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
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Resource
    private SysPermissionMapper permissionMapper;

    @Override
    public List<SysPermission> findPermissionsByRole(SysRole sysRole) {
        return permissionMapper.findPermissionsByRole(sysRole.getId());
    }

    @Override
    public List<SysPermission> findAllPermission() {
        return permissionMapper.findAllPermissions();
    }

    @Override
    public List<SysPermission> findAllPermissionMenu() {
        return permissionMapper.findAllPermissionsMenu();
    }

    @Override
    public Integer loadPermissionMaxOrderNum() {
        return permissionMapper.loadPermissionMaxOrderNum();
    }

    @Override
    public Integer modifyPermission(SysPermission permission) {
        return permissionMapper.modifyPermission(permission.getPid(), permission.getTitle(), permission.getPercode(), permission.getOrdernum(), permission.getAvailable(), permission.getId());
    }

    @Override
    public Integer examinemenuSubdivision(Integer id) {
        return permissionMapper.examinemenuSubdivision(id);
    }

    @Override
    public Integer modifyPermissionMenu(SysPermission permission) {
        return permissionMapper.modifyPermissionMenu(permission.getPid(), permission.getTitle(), permission.getHref(), permission.getIcon(), permission.getOpen(), permission.getAvailable(), permission.getOrdernum(), permission.getId());
    }

}
