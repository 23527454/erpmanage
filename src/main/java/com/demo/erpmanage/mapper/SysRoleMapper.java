package com.demo.erpmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.erpmanage.entity.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("select r.* from sys_role r inner join sys_role_user ru on r.id=ru.rid inner join sys_user u on u.id=ru.uid where u.id=#{id}")
    public List<SysRole> findRoleByUser(@Param("id") Integer userId);

    @Delete("delete from sys_role_permission where rid=#{id}")
    public Integer deleteRolePermissionByRoleId(@Param("id") Integer rid);

    @Insert("insert into sys_role_permission values(#{rid},#{pid})")
    public Integer saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);

    @Update("update sys_role set name=#{name},remark=#{remark},available=#{available} where id=#{id}")
    public Integer modifyRole(@Param("name") String name,
                              @Param("remark") String remark,
                              @Param("available") Integer available,
                              @Param("id") Integer id);
}
