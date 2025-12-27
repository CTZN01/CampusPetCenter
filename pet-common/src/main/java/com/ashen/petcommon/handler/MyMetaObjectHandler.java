package com.ashen.petcommon.handler;

import com.ashen.petcommon.utils.DateUtils;
import com.ashen.petcommon.utils.UserUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * MyBatis-Plus 元对象字段填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = getUserIdSafe();

        // 2. 插入填充建议使用 strictInsertFill (只有当字段为 null 时才填充，避免覆盖手动设置的值)
        this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        this.strictInsertFill(metaObject, "createTime", Date.class, DateUtils.now());

        // 插入时，updateBy/updateTime 通常也要初始化
        this.strictInsertFill(metaObject, "updateBy", Long.class, userId);
        this.strictInsertFill(metaObject, "updateTime", Date.class, DateUtils.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = getUserIdSafe();

        // 3. 更新时：只填更新人、更新时间
        // 【注意】因为使用了 strictUpdateFill，如果对象里的 updateBy 已经有值，这里将不会执行填充
        this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    private Long getUserIdSafe() {
        Long uid = UserUtils.getCurrentUserId();
        return uid != null ? uid : -1L;
    }

}
