package com.as1124.spring5.mybatis.mapper;

import com.as1124.spring5.entity.DeviceArp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceArpMapper {

    List<DeviceArp> getByDevice(String mgmtIp);

    List<DeviceArp> getAll();

    IPage<DeviceArp> getAllByPage(IPage pageInfo);

    int save(DeviceArp deviceArp);
}
