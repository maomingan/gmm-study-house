package com.gmm.design_mode.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Gmm
 * @date 2024/8/30
 */
@Controller
@RequestMapping("gmm-common/transformer")
@Slf4j
public class TransformerController {

    @Autowired
    TransformerFactory transformerFactory;

    @Autowired
    TransformerFactory2 transformerFactory2;

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        String type = "a1Transformer";
        transformerFactory.getTransformer(type).transform("aaaa");
        type = "AA2";
        transformerFactory.getTransformer(type).transform("aaaa");
        return "finish...";
    }

    @GetMapping("/test2")
    @ResponseBody
    public String test2(){
        String name = "A2";
        transformerFactory2.getTransformerByName(name).transform("aaaa");
        log.info("finished....");
        return "finish...";
    }

}
