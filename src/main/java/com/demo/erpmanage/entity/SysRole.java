package com.demo.erpmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
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
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String remark;
    private Integer available;
    private Date createtime;

    @TableField(exist = false)
    private List<SysPermission> permissionList = new ArrayList<SysPermission>();

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", name=" + name +
                ", remark=" + remark +
                ", available=" + available +
                ", createtime=" + createtime +
                "}";
    }
}
