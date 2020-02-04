package com.demo.erpmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String loginname;
    private String address;
    private Integer sex;
    private String remark;
    private String pwd;
    private Integer deptid;
    private Date hiredate;
    private Integer mgr;
    private Integer available;
    private Integer ordernum;

    /**
     * 用户类型[0超级管理员1，管理员，2普通用户]
     */
    private Integer type;

    /**
     * 头像地址
     */
    private String imgpath;
    private String salt;

    @TableField(exist = false)
    private List<SysRole> roleList;

    @TableField(exist = false)
    private SysDept dept;

    @TableField(exist = false)
    private String deptTitle;

    @TableField(exist = false)
    private String mgrName;

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", name=" + name +
                ", loginname=" + loginname +
                ", address=" + address +
                ", sex=" + sex +
                ", remark=" + remark +
                ", pwd=" + pwd +
                ", deptid=" + deptid +
                ", hiredate=" + hiredate +
                ", mgr=" + mgr +
                ", available=" + available +
                ", ordernum=" + ordernum +
                ", type=" + type +
                ", imgpath=" + imgpath +
                ", salt=" + salt +
                "}";
    }

    public SysUser(String loginname, String pwd) {
        this.loginname = loginname;
        this.pwd = pwd;
    }
}
