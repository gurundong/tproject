package com.gurundong.threadproject.thread.work.mapper;

import com.gurundong.threadproject.thread.work.bean.DO.ManagePermissionDO;
import com.gurundong.threadproject.thread.work.bean.DO.ManageUserPermissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InspurCloudBssMapper {
    public List<ManagePermissionDO> getAllPermission();
    public int initAllPermission(List<ManageUserPermissionDO> manageUserPermissionBeans);
}
