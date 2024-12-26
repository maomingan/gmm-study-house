package com.gmm.gmmwarehouse.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @author Gmm
 * @date 2024/12/25
 */
@Configuration
public class HiveDataSourceConfig {

    @Value("${hive.datasource.url}")
    private String url;

    @Value("${hive.datasource.username}")
    private String username;

    @Value("${hive.datasource.password}")
    private String password;

    @Value("${hive.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource hiveDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        // 可根据需要调整连接池大小
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(2);
        dataSource.setIdleTimeout(30000);
        dataSource.setConnectionTimeout(20000);
        return dataSource;
    }

}
