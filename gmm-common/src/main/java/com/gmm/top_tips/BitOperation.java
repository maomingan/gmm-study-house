package com.gmm.top_tips;

/**
 * 位运算的业务应用场景
 */
public class BitOperation {

    public static void main(String args[]) {

//        isOddOrEvenNumber(-12);
//        swapNoMidVar(1,2);
        is2N_best_solution(256);

    }

    /**
     * 场景1：判断是奇数还是偶数
     * 偶数的最低位为0，奇数的最低位为1，按位跟0（00000000）做与运算（都为1才为1，否则都为0），偶数肯定为0，奇数肯定为1
     */
    public static void isOddOrEvenNumber(int number) {

        if ((number & 1) == 0) {
            System.out.println("偶数");
        } else if ((number & 1) == 1) {
            System.out.println("奇数");
        }

    }

    /**
     * 场景2：不借助中间变量交换两个值
     * 两个特性：
     * 1、任何数和自己进行异或操作结果都为0
     * 2、异或符合交换律，即a^b = b^a
     */
    public static void swapNoMidVar(int a, int b) {

        System.out.println("开始时，a=" + a + ",b=" + b);
        a = a ^ b;
        // 实质为 b = b ^ a = b ^ (a ^ b) = a;
        b = a ^ b;
        // 实质为 a = a ^ b = (a ^ b) ^ (b ^ (a ^ b)) = (a ^ b) ^ a = b;
        a = a ^ b;
        System.out.println("结束时，a=" + a + ",b=" + b);

    }

    /**
     * 场景3：计算一个正整数是不是2的N次方
     * 思路：先发现规律，2的N次方用2进制位来表示，那么就是2：10，4:100,8:1000，16:10000....发现规律后，便可以方便的使用位运算来得到结果
     * 解决：如果它是一个这样的数，则它与自己减1的二进制取与，就得到0.      10000000 & 01111111 = 00000000即0
     *
     * @param inputNumber
     */
    public static void is2N_best_solution(int inputNumber) {

        if ((inputNumber & (inputNumber - 1)) == 0) {
            System.out.println(inputNumber + " is 2N number！");
        } else {
            System.out.println(inputNumber + " is not 2N number");
        }

    }

}
