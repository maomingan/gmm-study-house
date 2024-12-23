package com.gmm.workcase.async;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Gmm
 * @date 2022/5/24
 */
@Component
@Slf4j
public class AsyncTaskManager {

    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    Cache asyncTaskCache;

    public TaskInfo submit(AsyncTask task, CatalogTypeEnum taskType){
        String taskID = genTaskID();
        TaskInfo taskInfo = TaskInfo.builder()
                .task_id(taskID)
                .task_name(task.getTaskName())
                .submit_time(new Date())
                .task_status(TaskStatusEnum.SUBMITTED)
                .task_type(taskType)
                .input(task.getInput())
                .user_id(1)
                .build();
        setTask(taskInfo);
        // TODO 落库mysql
        asyncTaskExecutor.execute(task, taskID);
        return taskInfo;
    }

//    public TaskInfo submit(Runnable task, CatalogTypeEnum taskType){
//        String taskID = genTaskID();
//        TaskInfo taskInfo = TaskInfo.builder()
//                .task_id(taskID)
//                .submit_time(new Date())
//                .task_status(TaskStatusEnum.SUBMITTED)
//                .task_type(taskType)
//                .build();
//        setTaskInfo(taskInfo);
//        asyncTaskExecutor.executeRunnable(task, taskID);
//        return taskInfo;
//    }

    public void setTask(TaskInfo taskInfo){
        asyncTaskCache.put(taskInfo.getTask_id(), taskInfo);
    }

    public TaskInfo getTask(String taskID){
        return (TaskInfo) asyncTaskCache.getIfPresent(taskID);
    }

    public List<TaskInfo> getAllTask(){
        // TODO
        return null;
    }

    public void cancelTask(String taskID){
        asyncTaskExecutor.interrupt(taskID);
    }

    private String genTaskID(){
        return UUID.randomUUID().toString();
    }

}
