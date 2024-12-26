package com.gmm.gmmwarehouse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *  hive连接池方式操作hive
 * @author Gmm
 * @date 2024/12/23
 */
@Service
@Slf4j
public class HiveHandler {

    @Autowired
    private DataSource hiveDataSource;

    /**
     * HQL执行方法
     * @param hql
     */
    public void execute(String hql) {
        try (Connection connection = hiveDataSource.getConnection();
             Statement statement = connection.createStatement()) {

            boolean isResultSet = statement.execute(hql);
            if (isResultSet) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    log.info("Result: " + resultSet.getString(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3));
                }
            } else {
                int updateCount = statement.getUpdateCount();
                log.info("Update Count: " + updateCount);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}