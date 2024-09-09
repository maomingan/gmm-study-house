package com.gmm.design_mode.decorator;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 装饰器模式，适合需要对原有对象做增强操作的场景，比如打印日志，加密，缓存.
 * @author Gmm
 * @date 2024/9/9
 */
@Slf4j
@Controller
@ResponseBody
@RequestMapping("gmm-common/decorator")
public class DecoratorController {

    @Autowired
    DataService dataServiceImpl;

    @Autowired
    DataService dataDecorator;

    @GetMapping("/test")
    public String test(){
        JSONObject requestJson = new JSONObject();
        requestJson.put("mobile", "18767122586");
        log.info("未增强前做业务如下：");
        dataServiceImpl.execute(requestJson);
        log.info("---------------------");
        log.info("使用装饰类做增强后，做业务逻辑如下：");
        dataDecorator.execute(requestJson);
        return "finished...";
    }

}
