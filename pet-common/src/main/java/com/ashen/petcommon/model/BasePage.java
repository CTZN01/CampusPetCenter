package com.ashen.petcommon.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BasePage implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 当前页码（从 1 开始）
     */
    @TableField(exist = false)
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    @TableField(exist = false)
    private Integer pageSize = 10;
    /**
     * 查询类型 (COUNT_AND_LIST 列表和总数查询, LIST 仅列表查询, COUNT 仅总数查询)
     */
    @TableField(exist = false)
    private String selectType;
}
