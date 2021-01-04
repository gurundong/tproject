package com.gurundong.threadproject.thread.work.service;

import com.gurundong.threadproject.thread.common.utils.IdWorker;
import com.gurundong.threadproject.thread.work.bean.DO.ManagePermissionDO;
import com.gurundong.threadproject.thread.work.bean.DO.ManageUserPermissionDO;
import com.gurundong.threadproject.thread.work.mapper.InspurCloudBssMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SystemService {
    @Autowired
    private final InspurCloudBssMapper inspurCloudBssMapper;

    public SystemService(InspurCloudBssMapper inspurCloudBssMapper) {
        this.inspurCloudBssMapper = inspurCloudBssMapper;
    }

    /**
     * 初始化公有云BSS超级管理员
     * @return
     */
    public String initSuperAdminForPublicBss(String userId){
        try {
            List<ManagePermissionDO> managePermissionDOS = inspurCloudBssMapper.getAllPermission();
            List<ManageUserPermissionDO> userPermissionDOS = new ArrayList<>();
            managePermissionDOS.forEach(v -> {
                ManageUserPermissionDO manageUserPermissionDO = new ManageUserPermissionDO();
                manageUserPermissionDO.setId(String.valueOf(IdWorker.getNextId()));
                manageUserPermissionDO.setUserId(userId);
                manageUserPermissionDO.setCreatedUser(userId);
                manageUserPermissionDO.setUpdateUser(userId);
                userPermissionDOS.add(manageUserPermissionDO);
            });
            inspurCloudBssMapper.initAllPermission(userPermissionDOS);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error:"+e.getMessage();
        }
    }
}
