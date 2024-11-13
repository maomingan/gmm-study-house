package com.gmm.mybatis_generator;

/**
 * @author Gmm
 * @date 2024/11/13
 */
public class ApiController {

    protected <T> R<T> success(T data) {
        return R.ok(data);
    }

    protected R<?> failure(String message) {
        return R.failed(message);
    }

}
