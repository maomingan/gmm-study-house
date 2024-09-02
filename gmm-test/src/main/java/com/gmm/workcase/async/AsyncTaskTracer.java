package com.gmm.workcase.async;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Gmm
 * @date 2022/6/1
 */
@Component
@Aspect
@Slf4j
public class AsyncTaskTracer {

    @Autowired
    AsyncTaskManager asyncTaskManager;

    @Pointcut("execution(public * com.gmm.workcase.async.AsyncTaskExecutor.execute(..))")
    public void taskHandle(){}

    @Around("taskHandle()")
    public void doAround(ProceedingJoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        AsyncTask task = (AsyncTask) args[0];
        String taskID = args[1].toString();

        log.info("AsyncTaskTracer is tracing the task: {}, thread is: {}", taskID, Thread.currentThread().getName());
        TaskInfo taskInfo = asyncTaskManager.getTask(taskID);
        taskInfo.setTask_status(TaskStatusEnum.RUNNING);
        Date runTime = new Date();
        taskInfo.setRun_time(runTime);
        asyncTaskManager.setTask(taskInfo);

        TaskStatusEnum status = TaskStatusEnum.FAILED;
        try {
            joinPoint.proceed(args);
            status = TaskStatusEnum.SUCCESS;
        } catch (Throwable throwable) {
            log.error("async task {} execute failed, error info: {}", taskID, throwable);
        }

        taskInfo.setOutput(task.getOutput());
        taskInfo.setExt(task.getExt());
        taskInfo.setTask_status(status);
        Date finishTime = new Date();
        taskInfo.setFinish_time(finishTime);
        taskInfo.setTotal_spend((finishTime.getTime()-runTime.getTime())/1000);
        asyncTaskManager.setTask(taskInfo);

    }

}
