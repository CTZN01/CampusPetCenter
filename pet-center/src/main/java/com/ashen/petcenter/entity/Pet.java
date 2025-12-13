package com.ashen.petcenter.entity;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;

@Data
@Schema(description = "Pet 实体")
@TableName("pet")
public class Pet implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键 id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "年龄")
    private Integer age;
}

