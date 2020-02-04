package com.demo.erpmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.erpmanage.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 * l
 *
 * @author gzd
 * @since 2019-12-31
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Select("select p.* from sys_permission p inner join sys_role_permission rp on rp.pid=p.id inner join sys_role r on r.id=rp.rid where r.id=#{id} and type='menu'")
    public List<SysPermission> findPermissionsByRole(@Param("id") Integer roleId);

    @Select("select * from sys_permission")
    public List<SysPermission> findAllPermissions();

    @Select("select * from sys_permission where type='menu'")
    public List<SysPermission> findAllPermissionsMenu();

    @Select("select max(ordernum) from sys_permission")
    public Integer loadPermissionMaxOrderNum();

    @Update("update sys_permission set pid=#{pid},title=#{title},percode=#{percode},ordernum=#{ordernum},available=#{available} where id=#{id}")
    public Integer modifyPermission(@Param("pid") Integer pid,
                                    @Param("title") String title,
                                    @Param("percode") String percode,
                                    @Param("ordernum") Integer ordernum,
                                    @Param("available") Integer available,
                                    @Param("id") Integer id);

    @Select("select count(*) from sys_permission where pid=#{id}")
    public Integer examinemenuSubdivision(@Param("id") Integer id);

    @Update("update sys_permission set pid=#{pid},title=#{title},href=#{href},icon=#{icon},open=#{open},available=#{available},ordernum=#{ordernum} where id=#{id}")
    public Integer modifyPermissionMenu(@Param("pid") Integer pid,
                                        @Param("title") String title,
                                        @Param("href") String href,
                                        @Param("icon") String icon,
                                        @Param("open") Integer open,
                                        @Param("available") Integer available,
                                        @Param("ordernum") Integer ordernum,
                                        @Param("id") Integer id);
}
