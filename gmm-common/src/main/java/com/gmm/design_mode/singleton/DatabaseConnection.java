package com.gmm.design_mode.singleton;

import org.springframework.stereotype.Component;

/**
 * @author Gmm
 * @date 2024/9/9
 */
@Component
public class DatabaseConnection {

    private DatabaseConnection connection;

    private DatabaseConnection(){
        // 防止实例化的私有构造函数
    }

    public synchronized DatabaseConnection getConnection(){
        if(connection == null){
            connection = new DatabaseConnection();
        }
        return connection;
    }

}
