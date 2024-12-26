package com.gmm.gmmwarehouse.job;

import com.gmm.gmmwarehouse.service.HiveHandler;
import com.gmm.gmmwarehouse.service.HiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Gmm
 * @date 2024/12/23
 */
@Component
@Slf4j
public class HiveScheduler {

    /**
     * 不推荐，可能会出现连接泄露
     */
    @Autowired
    private HiveService hiveService;

    /**
     * 推荐
     */
    @Autowired
    private HiveHandler hiveHandler;

    @Scheduled(cron = "0 0 1 * * ?")
    public void executeHiveTask() {
        log.info("hive job start...");
        String query = "SELECT * FROM person LIMIT 10";
//        hiveService.execute(query);
        hiveHandler.execute(query);
        String insert = "INSERT INTO person VALUES (20002, 'springboot cron hiveHandler insert', 999)";
//        hiveService.execute(insert);
        hiveHandler.execute(insert);
        log.info("hive job finished...");
    }

}
