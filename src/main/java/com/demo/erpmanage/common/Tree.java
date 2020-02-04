package com.demo.erpmanage.common;

import lombok.Data;

@Data
public class Tree {
    private Integer id;
    private Integer parentId;
    private String title;
    private String checkArr;

    public Tree() {
    }


    public Tree(Integer id, Integer parentId, String title, String checkArr) {
        this.id = id;
        this.parentId = parentId;
        this.title = title;
        this.checkArr = checkArr;
    }
}
