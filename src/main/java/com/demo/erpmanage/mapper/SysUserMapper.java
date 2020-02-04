package com.demo.erpmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.erpmanage.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("delete from sys_role_user where uid=#{uid}")
    public Integer delUserRoleByUserId(@Param("uid") Integer uid);

    @Select("insert into sys_role_user(rid,uid) values (#{rid},#{uid})")
    public Integer addUserRole(@Param("uid") Integer uid, @Param("rid") Integer rid);

    @Update("update sys_user set pwd=#{pwd} where id=#{id}")
    public Integer resetPwd(@Param("id") Integer id, @Param("pwd") String pwd);

    @Select("select * from sys_user where deptid=#{id}")
    public List<SysUser> findUserByDeptId(@Param("id") Integer id);

    @Select("select max(ordernum) from sys_user")
    public Integer loadUserMaxOrderNum();

    @Select("select * from sys_user where id=#{mgr}")
    public SysUser loadUserbyId(@Param("mgr") Integer mgr);

    @Insert("insert into sys_user values(default,#{name},#{loginname},#{address},#{sex},#{remark},#{pwd},#{deptid},NOW(),#{mgr},#{available},#{ordernum},#{type},null,null)")
    public Integer addUser(@Param("name") String name,
                           @Param("loginname") String loginname,
                           @Param("address") String address,
                           @Param("sex") Integer sex,
                           @Param("remark") String remark,
                           @Param("pwd") String pwd,
                           @Param("deptid") Integer deptid,
                           @Param("mgr") Integer mgr,
                           @Param("available") Integer available,
                           @Param("ordernum") Integer ordernum,
                           @Param("type") Integer type
    );

    @Update("update sys_user set name=#{name},loginname=#{loginname},address=#{address},sex=#{sex},remark=#{remark},deptid=#{deptid},mgr=#{mgr},available=#{available},ordernum=#{ordernum} where id=#{id}")
    public Integer modifyUser(@Param("name") String name,
                              @Param("loginname") String loginname,
                              @Param("address") String address,
                              @Param("sex") Integer sex,
                              @Param("remark") String remark,
                              @Param("deptid") Integer deptid,
                              @Param("mgr") Integer mgr,
                              @Param("available") Integer available,
                              @Param("ordernum") Integer ordernum,
                              @Param("id") Integer id);

    @Select("select count(*) from sys_user where loginname=#{loginname}")
    public Integer findLoginNameCount(@Param("loginname") String loginname);
}
