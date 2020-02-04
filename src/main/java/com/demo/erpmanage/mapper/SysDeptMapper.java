package com.demo.erpmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.erpmanage.entity.SysDept;
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
public interface SysDeptMapper extends BaseMapper<SysDept> {
    @Select("select * from sys_dept")
    public List<SysDept> findAllDept();

    @Select("select max(ordernum) from sys_dept")
    public Integer loadDeptMaxOrderNum();

    @Select("select count(*) from sys_dept where pid=#{id}")
    public Integer examineDeptSubdivision(@Param("id") Integer id);

    @Update("update sys_dept set pid=#{pid},title=#{title},address=#{address},remark=#{remark},open=#{open},available=#{available},ordernum=#{ordernum} where id=#{id}")
    public Integer modifyDept(@Param("pid") Integer pid,
                              @Param("title") String title,
                              @Param("address") String address,
                              @Param("remark") String remark,
                              @Param("open") Integer open,
                              @Param("available") Integer available,
                              @Param("ordernum") Integer ordernum,
                              @Param("id") Integer id);
}
