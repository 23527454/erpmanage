package com.demo.erpmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author gzd
 * @since 2019-12-31
 */
@Data
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer pid;

    /**
     * 权限类型[menu/permission]
     */
    private String type;
    private String title;

    /**
     * 权限编码[只有type= permission才有  user:view]
     */
    private String percode;
    private String icon;
    private String href;
    private String target;
    private Integer open;
    private Integer ordernum;

    /**
     * 状态【0不可用1可用】
     */
    private Integer available;

    @Override
    public String toString() {
        return "SysPermission{" +
                "id=" + id +
                ", pid=" + pid +
                ", type=" + type +
                ", title=" + title +
                ", percode=" + percode +
                ", icon=" + icon +
                ", href=" + href +
                ", target=" + target +
                ", open=" + open +
                ", ordernum=" + ordernum +
                ", available=" + available +
                "}";
    }
}
