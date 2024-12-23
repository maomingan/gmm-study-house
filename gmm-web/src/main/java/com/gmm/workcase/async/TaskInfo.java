package com.gmm.workcase.async;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Gmm
 * @date 2022/5/24
 */
@Builder
@Data
@ToString
public class TaskInfo {

    private String task_id;
    private String task_name;
    private CatalogTypeEnum task_type;
    private TaskStatusEnum task_status;
    private Date submit_time;
    private Date run_time;
    private Date finish_time;
    private Long total_spend;
    private String input;
    private String output;
    private String ext;
    private int priority;
    private int user_id;

}
