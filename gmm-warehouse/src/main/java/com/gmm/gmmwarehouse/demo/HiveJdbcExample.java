package com.gmm.gmmwarehouse.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Gmm
 * @date 2024/12/17
 */
public class HiveJdbcExample {
    private static final String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
    private static final String URL = "jdbc:hive2://ddp1:10000/gmmdb"; // HiveServer2 地址和端口
    private static final String USER = "root"; // 用户名，默认情况下可以留空
    private static final String PASSWORD = "root"; // 密码

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            // 1. 加载 Hive JDBC 驱动
            Class.forName(DRIVER_NAME);
            System.out.println("Hive JDBC 驱动加载成功");

            // 2. 获取连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("成功连接到 Hive");

            // 3. 创建 Statement 对象
            statement = connection.createStatement();

            // 4. 执行查询操作
            String querySQL = "SELECT * FROM person LIMIT 10";
            System.out.println("执行查询: " + querySQL);
            ResultSet resultSet = statement.executeQuery(querySQL);

            while (resultSet.next()) {
                // 根据实际表结构，读取字段数据
                System.out.println("Result: " + resultSet.getString(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3));
            }

            // 5. 执行插入操作
            String insertSQL = "INSERT INTO person VALUES (10002, 'hiveJdbcInsert', 99)";
            System.out.println("执行插入: " + insertSQL);
            statement.execute(insertSQL);
            System.out.println("数据插入成功");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭资源
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
