package com.gmm.design_mode.strategy;

import org.springframework.stereotype.Service;

/**
 * @author Gmm
 * @date 2024/9/9
 */
@Service
public class CntScoreStrategy implements ScoreStrategy{

    @Override
    public String getScoreType() {
        return ScoreTypeEnum.CNT.getType();
    }

    @Override
    public String score() {
        return ScoreTypeEnum.CNT.getDesc();
    }
}
