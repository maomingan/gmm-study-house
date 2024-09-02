package com.gmm.funtion_coding;

/**
 * @author Gmm
 * @date 2024/8/2
 */
public class TestMain {

    public static void main(String[] args) {

        testThrow();


    }

    /**
     * 使用函数式接口美化代码场景：判断后抛出异常的代码
     */
    public static void testThrow(){
        int number = 2;

        // 普通方式会用if判断后直接抛出异常，但是代码中如果出现一堆的if else就会很难看
//        if (number > 1) {
//            throw new RuntimeException("number值大于1，不允许！");
//        }

        // 使用函数式接口，抛出异常是没有返回值的，定义为consumer消费型接口
        FunctionUtil.needThrow(number > 1).throwMsg("number值大于1，不允许！");
    }

}
