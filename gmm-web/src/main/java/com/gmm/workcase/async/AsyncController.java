package com.gmm.workcase.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Gmm
 * @date 2022/5/20
 */
@RestController
@RequestMapping("/case")
@Slf4j
public class AsyncController {

    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private TestBusService testBusService;



    /**
     * 工作场景案例：前台请求后天进行模型训练，但是训练的时间特别长，所以需要先返回前台提交任务成功，然后再异步执行训练任务，拿到训练结果异步入库。
     */
    @RequestMapping("/asyncCall")
    public String asyncCall(){
        String input = "asyncCall input params";
        TestTask task = new TestTask();
        task.setTaskName("测试异步任务");
        task.setInput(input);
        task.setTestBusService(testBusService);
        TaskInfo submit = asyncTaskManager.submit(task, CatalogTypeEnum.modeling);
        log.info("主线程逻辑继续");
        return submit.toString();
    }


    @RequestMapping("/asyncCall2")
    public String asyncCall2(){

        TaskInfo taskInfo = asyncTaskManager.submit(new AsyncTask() {
            @Override
            public void async() {
                log.info("开始异步任务...");
                try {
                    testBusService.buildBus("what");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("异步任务执行完成，拿到结果，入库...");
            }
        }, CatalogTypeEnum.xgb);

//        TaskInfo taskInfo = asyncTaskManager.submit(new Runnable() {
//            @Override
//            public void run() {
//                log.info("开始异步任务...");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("异步任务执行完成，拿到结果，入库...");
//            }
//        });

        try {
            Thread.sleep(1000);
            log.info("主线程逻辑继续...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return taskInfo.toString();

    }

    @RequestMapping("/getAllTask")
    public List<TaskInfo> getAllTask(){
        return null;
    }

    @PostMapping ("/getTask")
    public TaskInfo getTask(@RequestBody String taskID){
        return asyncTaskManager.getTask(taskID);
    }

    @PostMapping("/cancelTask")
    public void cancelTask(@RequestBody String taskID){
        asyncTaskManager.cancelTask(taskID);
    }

}
