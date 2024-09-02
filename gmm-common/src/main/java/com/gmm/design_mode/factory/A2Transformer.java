package com.gmm.design_mode.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Gmm
 * @date 2024/8/30
 */
@Service("AA2")
@Slf4j
//@Qualifier("AA2")
public class A2Transformer implements Transformer{
    @Override
    public String getName() {
        return "A2";
    }

    @Override
    public void transform(String input) {
        System.out.println("A2 tranform finished...");
    }
}
