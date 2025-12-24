package com.ashen.petcommon.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "基础实体类")
public class BaseEntity extends BasePage{
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField("CREATE_BY")
    @Schema(description = "创建人")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField("UPDATE_BY")
    @Schema(description = "更新人")
    private Long updateBy;
}
