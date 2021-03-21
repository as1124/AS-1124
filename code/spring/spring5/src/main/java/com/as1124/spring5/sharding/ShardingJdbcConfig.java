package com.as1124.spring5.sharding;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 这里演示对是不通过Spring Boot自动配置进行Sharding-JDBC环境装配对过程
 *
 * @author As-1124
 */
@AutoConfigureBefore(SpringBootConfiguration.class)
@Configuration
public class ShardingJdbcConfig {

    private static final String SHARDING_COLUMN = "source_device";

    /**
     * 这里的示例只进行分表，不进行分库
     *
     * @param properties 数据源配置属性
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource createShardingDatasource(DataSourceProperties properties) {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }

        Map<String, DataSource> dataSourceMap = new HashMap<>(2, 1.0f);
        dataSourceMap.put("basic", dataSource);

        // 分表对逻辑表名
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("device_arp",
                "basic.device_arp_${0..1}");
        tableRuleConfiguration.setTableShardingStrategyConfig(getStandardStrategy());

        ShardingRuleConfiguration shardingConfiguration = new ShardingRuleConfiguration();
        shardingConfiguration.setDefaultDataSourceName("basic");
        shardingConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);

        try {
            return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingConfiguration, new Properties());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dataSource;
    }

    private ShardingStrategyConfiguration getStandardStrategy() {
        PreciseShardingAlgorithm<String> algorithm = new PreciseShardingAlgorithm<String>() {
            @Override
            public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
                String logicTableName = shardingValue.getLogicTableName();
                String columnValue = shardingValue.getValue();
                if (columnValue == null) {
                    return logicTableName + "_0";
                } else {
                    int hashValue = columnValue.hashCode();
                    if (hashValue < 0) {
                        hashValue = ~hashValue;
                    }
                    String realTableName = logicTableName + "_" + hashValue % 2;
                    System.out.println(realTableName);
                    return realTableName;
                }
            }
        };
        return new StandardShardingStrategyConfiguration(SHARDING_COLUMN, algorithm);
    }

    private ShardingStrategyConfiguration getInlineStrategy() {
        return new InlineShardingStrategyConfiguration(SHARDING_COLUMN, "device_arp${source_device % 2}");
    }
}
