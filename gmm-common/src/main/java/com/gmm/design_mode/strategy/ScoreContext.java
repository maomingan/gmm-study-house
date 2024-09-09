package com.gmm.design_mode.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gmm
 * @date 2024/9/9
 */
@Component
@Slf4j
public class ScoreContext {

    private final Map<String, ScoreStrategy> scoreStrategyMap = new ConcurrentHashMap<>();

    @Autowired
    public ScoreContext(List<ScoreStrategy> strategyList){
        strategyList.forEach(strategy -> scoreStrategyMap.put(strategy.getScoreType(), strategy));
    }

    public String score(String scoreType){
        final ScoreStrategy scoreStrategy = scoreStrategyMap.get(scoreType);
        if(scoreStrategy == null){
            return "找不到此类型的评分策略!";
        }
        return scoreStrategy.score();
    }

}
