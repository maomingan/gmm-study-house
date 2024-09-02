package com.gmm.design_mode.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Gmm
 * @date 2024/8/30
 */
@Service
@Slf4j
public class A1Transformer implements Transformer{

    @Override
    public String getName() {
        return "A1";
    }

    @Override
    public void transform(String input) {
        System.out.println("A1 transform finished...");
    }
}
