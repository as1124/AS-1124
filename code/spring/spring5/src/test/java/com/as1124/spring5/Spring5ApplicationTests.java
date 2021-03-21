package com.as1124.spring5;

import com.as1124.spring5.entity.DeviceArp;
import com.as1124.spring5.mybatis.mapper.DeviceArpMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.List;

@SpringBootTest(classes = Spring5Application.class)
class Spring5ApplicationTests {

    @Resource
    DeviceArpMapper deviceArpMapper;

    @Test
    public void testDeviceArp() {
        Assert.notNull(deviceArpMapper);

        String sourcePrefix = "127.0.0.";
        String arpPrefix = "192.168.1.";
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            int end = random.nextInt(256);
            DeviceArp arpPo = new DeviceArp();
            arpPo.setSourceDevice(sourcePrefix + end).setDeviceIp(arpPrefix + end)
                    .setDeviceMac("a124-1234-5678").setType("D");
            int rows = deviceArpMapper.save(arpPo);
            Assert.isTrue(rows == 1);
        }

    }

    @Test
    public void testSelect() {
        List<DeviceArp> result0 = deviceArpMapper.getByDevice("127.0.0.1");

        List<DeviceArp> allData = deviceArpMapper.getAll();
        System.out.println(allData.size());

        IPage<DeviceArp> result1 = deviceArpMapper.getAllByPage(new Page(1, 2, true));
        System.out.println(result1.getTotal());
    }

}
