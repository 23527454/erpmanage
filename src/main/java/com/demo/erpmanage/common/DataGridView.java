package com.demo.erpmanage.common;

import lombok.Data;

@Data
public class DataGridView {

    private Integer code = 0;
    private String msg = "";
    private Integer count = 0;
    private Object data;

    public DataGridView(Integer count, Object data) {
        this.count = count;
        this.data = data;
    }

    public DataGridView() {
    }
}
