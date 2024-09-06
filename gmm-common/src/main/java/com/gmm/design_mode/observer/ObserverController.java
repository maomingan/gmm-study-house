package com.gmm.design_mode.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 观察者模式
 * @author Gmm
 * @date 2024/9/6
 */
@Controller
@RequestMapping("gmm-common/observer")
public class ObserverController {

    @Autowired
    ObserverService observerService;

    @GetMapping("test")
    @ResponseBody
    public String test(){
        observerService.doSomeThing();
        return "finished...";
    }

}
