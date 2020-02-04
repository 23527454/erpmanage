package com.demo.erpmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
public interface SysUserService extends IService<SysUser> {
    /**
     * 登录
     *
     * @param queryWrapper
     * @return
     */
    public SysUser login(QueryWrapper<SysUser> queryWrapper);

    public IPage<SysUser> findAllUser(IPage<SysUser> page, QueryWrapper<SysUser> queryWrapper);

    public boolean modifyUserRole(Integer uid, Integer[] ids);

    public Integer resetPwd(Integer id, String pwd);

    public List<SysUser> findUserByDeptId(Integer id);

    public Integer loadUserMaxOrderNum();

    public SysUser loadUserbyId(Integer mgr);

    public Integer addUser(SysUser user);

    public Integer modifyUser(SysUser user);

    public boolean findLoginNameCount(String loginname);
}
