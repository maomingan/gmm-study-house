package com.gmm.design_mode.observer;

import lombok.Builder;
import lombok.Data;

/**
 * @author Gmm
 * @date 2024/9/6
 */
@Data
@Builder
public class MyEventData {

    private String name;
    private String msg;

}
