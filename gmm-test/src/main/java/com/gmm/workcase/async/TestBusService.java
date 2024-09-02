package com.gmm.workcase.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Gmm
 * @date 2022/6/1
 */
@Service
@Slf4j
public class TestBusService {

    public String buildBus(String input){
        log.info("调用service层业务逻辑，输入：{}", input);
        return input + "[bus return]";
    }

}
