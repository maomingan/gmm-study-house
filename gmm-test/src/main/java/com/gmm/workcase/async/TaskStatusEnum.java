package com.gmm.workcase.async;

import lombok.Getter;

/**
 * @author Gmm
 * @date 2022/5/24
 */
public enum TaskStatusEnum {

    SUBMITTED(0, "任务已提交"),
    RUNNING(1, "任务正在运行"),
    SUCCESS(2, "任务执行成功"),
    FAILED(-2, "任务执行失败");

    @Getter
    private int state;
    @Getter
    private String statInfo;

    TaskStatusEnum(int state, String statInfo) {
        this.state = state;
        this.statInfo = statInfo;
    }

}
