package com.demo.erpmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.erpmanage.entity.SysUser;
import com.demo.erpmanage.mapper.SysUserMapper;
import com.demo.erpmanage.service.SysUserService;
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
//@CacheConfig(cacheNames = "userService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper userMapper;

    @Override
    //@Cacheable(value = "getSysUserListBiz",keyGenerator = "keyGenerator")
    public SysUser login(QueryWrapper<SysUser> queryWrapper) {
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<SysUser> findAllUser(IPage<SysUser> page, QueryWrapper<SysUser> queryWrapper) {
        return userMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean modifyUserRole(Integer uid, Integer[] ids) {
        try {
            userMapper.delUserRoleByUserId(uid);
            if (ids != null && ids.length > 0) {
                for (Integer rid : ids) {
                    userMapper.addUserRole(uid, rid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Integer resetPwd(Integer id, String pwd) {
        return userMapper.resetPwd(id, pwd);
    }

    @Override
    public List<SysUser> findUserByDeptId(Integer id) {
        return userMapper.findUserByDeptId(id);
    }

    @Override
    public Integer loadUserMaxOrderNum() {
        return userMapper.loadUserMaxOrderNum();
    }

    @Override
    public SysUser loadUserbyId(Integer mgr) {
        return userMapper.loadUserbyId(mgr);
    }

    @Override
    public Integer addUser(SysUser user) {
        return userMapper.addUser(user.getName(), user.getLoginname(), user.getAddress(), user.getSex(), user.getRemark()
                , user.getPwd(), user.getDeptid(), user.getMgr(), user.getAvailable(), user.getOrdernum(), user.getType());
    }

    @Override
    public Integer modifyUser(SysUser user) {
        return userMapper.modifyUser(user.getName(), user.getLoginname(), user.getAddress(), user.getSex(), user.getRemark()
                , user.getDeptid(), user.getMgr(), user.getAvailable(), user.getOrdernum(), user.getId());
    }

    @Override
    public boolean findLoginNameCount(String loginname) {
        Integer result = userMapper.findLoginNameCount(loginname);
        if (result > 0) {
            return true;
        }
        return false;
    }
}
