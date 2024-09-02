package com.gmm.workcase.async;

import com.gmm.config.SpringBeanConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Gmm
 * @date 2022/5/31
 */
@Slf4j
@Data
public class TestTask extends AsyncTask {

    private TestBusService testBusService;
    private int sleep = 300*1000;

    @Override
    public void async() throws InterruptedException {
        log.info("业务执行前，入参："+input);
        testBusService.buildBus("hahaha, sleep "+sleep/1000+" s");
        Thread.sleep(sleep);
        String ret = SpringBeanConfig.getBean(TestBusService.class).buildBus("after ns, SpringBeanConfig get bean hahaha");
        this.setOutput(ret);
        this.setExt("catalogInfo:xxxx");
        // 测试异常
//        int aaa = 0;
//        int rrr = 100/aaa;
        log.info("业务执行完成，拿到结果，入库...");
    }

}
