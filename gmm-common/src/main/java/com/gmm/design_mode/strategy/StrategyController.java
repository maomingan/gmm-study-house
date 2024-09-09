package com.gmm.design_mode.strategy;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 策略模式，试用于不同的执行办法的业务场景，像是在工厂模式的基础上，再封装一个context类统一执行策略
 * @author Gmm
 * @date 2024/9/9
 */
@Controller
@Slf4j
@RequestMapping("gmm-common/strategy")
@ResponseBody
public class StrategyController {

    @Autowired
    ScoreContext scoreContext;

    @PostMapping("/test")
    public String test(@RequestBody JSONObject request){
        return scoreContext.score(request.getString("type"));
    }

}
