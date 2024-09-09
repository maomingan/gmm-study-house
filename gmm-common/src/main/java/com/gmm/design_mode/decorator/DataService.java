package com.gmm.design_mode.decorator;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Gmm
 * @date 2024/9/9
 */
public interface DataService {

    void execute(JSONObject requestData);

}
