package com.gmm.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gmm
 * @date 2024/9/9
 */
@AllArgsConstructor
@Getter
public enum ServiceTypeEnum {
    /**
     * 0 默认
     */
    DEFAULT(0, "默认"),
    /**
     * 1 外数内部流水号
     */
    DATA_SERVICE(1, "外数内部流水号");

    private final int code;
    private final String desc;
}
