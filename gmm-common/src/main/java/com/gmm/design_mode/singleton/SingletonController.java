package com.gmm.design_mode.singleton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 单例模式，全局只有一个实例化对象，作为全局访问点。适合数据库连接、缓存对象等应用场景。
 * @author Gmm
 * @date 2024/9/9
 */
@Slf4j
@Controller
@RequestMapping("gmm-common/singleton")
@ResponseBody
public class SingletonController {

    @Autowired
    DatabaseConnection databaseConnection;

    @RequestMapping("/test")
    public String test(){
        final DatabaseConnection connection1 = databaseConnection.getConnection();
        log.info("获取到连接实例1：{}", connection1.toString());
        final DatabaseConnection connection2 = databaseConnection.getConnection();
        log.info("获取到连接实例2：{}", connection2.toString());
        if(connection1 == connection2){
            log.info("两个实例对象相同");
        }else{
            log.info("两个实例对象不同");
        }
        return "finished...";
    }

}
