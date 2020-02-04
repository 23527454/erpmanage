package com.demo.erpmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
@Data
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String title;
    private Integer open;
    private String remark;
    private String address;

    /**
     * 状态【0不可用1可用】
     */
    private Integer available;

    /**
     * 排序码【为了调事显示顺序】
     */
    private Integer ordernum;
    private Date createtime;


    @Override
    public String toString() {
        return "SysDept{" +
                "id=" + id +
                ", pid=" + pid +
                ", title=" + title +
                ", open=" + open +
                ", remark=" + remark +
                ", address=" + address +
                ", available=" + available +
                ", ordernum=" + ordernum +
                ", createtime=" + createtime +
                "}";
    }
}
