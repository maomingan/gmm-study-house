package com.gmm.funtion_coding;

/**
 * @author Gmm
 * @date 2024/8/2
 */
public class FunctionUtil {

    // 返回函数式接口类型
    public static ThrowExceptionFunction needThrow(boolean need) {
        return (errMsg -> {
            if (need) {
                throw new RuntimeException(errMsg);
            }
        });
    }

}
