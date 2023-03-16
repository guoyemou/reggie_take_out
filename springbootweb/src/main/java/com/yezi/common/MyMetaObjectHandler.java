package com.yezi.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yezi.utils.BaseContextUtils;
import com.yezi.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
    log.info("公共字段自动填充[insert]");
    metaObject.setValue("createTime", DateUtils.parse(new Date()));
    metaObject.setValue("updateTime", DateUtils.parse(new Date()));
    metaObject.setValue("createUser", BaseContextUtils.getCurrentId());
    metaObject.setValue("updateUser", BaseContextUtils.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    log.info("公共字段自动填充[update]");
        metaObject.setValue("updateTime", DateUtils.parse(new Date()));
        metaObject.setValue("updateUser", BaseContextUtils.getCurrentId());
    }
}
