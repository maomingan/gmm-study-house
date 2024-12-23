package com.gmm.workcase.async;

import lombok.Data;

/**
 * @author Gmm
 * @date 2022/6/1
 */
@Data
public abstract class AsyncTask {

    public String taskName;
    public String input;
    public String output;
    public String ext;

    public abstract void async() throws InterruptedException;

}
