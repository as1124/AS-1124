package com.as1124.spring5.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("DeviceArp")
@Accessors(chain = true)
@Data
public class DeviceArp {

    private Integer id;

    private String sourceDevice;

    private String deviceIp;

    private String deviceMac;

    private String type;

    private Date updateTime;

}
