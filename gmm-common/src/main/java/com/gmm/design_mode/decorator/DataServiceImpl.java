package com.gmm.design_mode.decorator;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Gmm
 * @date 2024/9/9
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService{

    @Override
    public void execute(JSONObject requestData) {
        final String mobile = requestData.getString("mobile");
        log.info("使用请求入参里的主键处理业务逻辑，mobile：{}", mobile);
    }

}
