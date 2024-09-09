package com.gmm.design_mode.strategy;

import org.springframework.stereotype.Service;

/**
 * @author Gmm
 * @date 2024/9/9
 */
@Service
public class AvgScoreStrategy implements ScoreStrategy{

    @Override
    public String getScoreType() {
        return ScoreTypeEnum.AVG.getType();
    }

    @Override
    public String score() {
        return ScoreTypeEnum.AVG.getDesc();
    }
}
