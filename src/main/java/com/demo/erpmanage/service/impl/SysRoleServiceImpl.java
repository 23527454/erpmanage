package com.demo.erpmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.erpmanage.entity.SysRole;
import com.demo.erpmanage.entity.SysUser;
import com.demo.erpmanage.mapper.SysRoleMapper;
import com.demo.erpmanage.service.SysRoleService;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper roleMapper;

    @Override
    public List<SysRole> findRoleByUser(SysUser user) {
        return roleMapper.findRoleByUser(user.getId());
    }

    @Override
    public IPage<SysRole> findRoleByPage(IPage<SysRole> page, QueryWrapper<SysRole> queryWrapper) {
        return roleMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean saveRolePermission(Integer rid, Integer[] ids) {
        try {
            Integer result = roleMapper.deleteRolePermissionByRoleId(rid);
            if (ids != null && ids.length > 0) {
                for (Integer pid : ids) {
                    roleMapper.saveRolePermission(rid, pid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Integer modifyRole(SysRole role) {
        return roleMapper.modifyRole(role.getName(), role.getRemark(), role.getAvailable(), role.getId());
    }
}
