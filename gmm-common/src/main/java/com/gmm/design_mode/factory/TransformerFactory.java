package com.gmm.design_mode.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Gmm
 * @date 2024/8/30
 */
@Service
public class TransformerFactory {

    @Autowired
    private Map<String, Transformer> transformerMap;

    public Transformer getTransformer(String type){
        if(transformerMap.containsKey(type)){
            return transformerMap.get(type);
        }
        return null;
    }

}
