package com.gmm.workcase.queue;

import com.gmm.workcase.async.TaskStatusEnum;
import lombok.*;

import java.util.Date;

/**
 * @author Gmm
 * @date 2022/6/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MpTaskInfo implements Comparable{

    private String mpTaskId;
    private String taskName;
    private Date submitTime;
    private Boolean isSyncTask;

    @Override
    public int compareTo(Object o) {
        if(o instanceof MpTaskInfo){
            MpTaskInfo o2 = ((MpTaskInfo) o);
            if(this.isSyncTask && !o2.isSyncTask){
                return -1;
            }else if(!this.isSyncTask && o2.isSyncTask){
                return 1;
            }else{
                if(this.getSubmitTime().before(o2.getSubmitTime())){
                    return -1;
                }else if(this.getSubmitTime().after(o2.getSubmitTime())){
                    return 1;
                }else{
                    return this.getSubmitTime().compareTo(o2.getSubmitTime());
                }
            }
        }else{
            return 0;
        }
    }
}
