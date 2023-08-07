package com.shbone.reggiedemo.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("公共字段自动插入填充");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getThreadLocal());
        metaObject.setValue("updateUser",BaseContext.getThreadLocal());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("公共字段自动更新填充");
        long id = Thread.currentThread().getId();
        log.info("线程号:"+id);
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getThreadLocal());
    }
}
