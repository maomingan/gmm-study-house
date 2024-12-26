package com.gmm.gmmwarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GmmWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmmWarehouseApplication.class, args);
    }

}
