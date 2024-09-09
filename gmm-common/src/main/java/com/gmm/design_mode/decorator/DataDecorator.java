package com.gmm.design_mode.decorator;

import com.alibaba.fastjson.JSONObject;
import com.gmm.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Gmm
 * @date 2024/9/9
 */
@Component
@Slf4j
public class DataDecorator implements DataService{

    @Autowired
    private DataServiceImpl dataServiceImpl;

//    private DataServiceImpl dataServiceImpl;
//    public DataDecorator(DataServiceImpl dataServiceImpl){
//        this.dataServiceImpl = dataServiceImpl;
//    }

    @Override
    public void execute(JSONObject requestData) {

        String mobile = requestData.getString("mobile");
        log.info("加密增强前，原mobile明文为：{}", mobile);
        mobile = HashUtil.getMD5(mobile);
        log.info("加密增强后，md5加密mobile为：{}", mobile);

        requestData.put("mobile", mobile);
        dataServiceImpl.execute(requestData);

        log.info("加入处理完成后的日志增强.");

    }
}
