package com.gmm.design_mode.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gmm
 * @date 2024/9/9
 */
@Getter
@AllArgsConstructor
public enum ScoreTypeEnum {

    SUM("sum", "求和的方式计算评分"),
    CNT("cnt", "累计的方式计算评分"),
    AVG("avg", "平均值的方式计算评分")
    ;

    private String type;
    private String desc;

}
