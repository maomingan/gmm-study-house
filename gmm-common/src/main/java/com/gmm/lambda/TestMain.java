package com.gmm.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Gmm
 * @date 2024/8/2
 */
public class TestMain {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(2,3,1,4,5,6);
        final Map<Integer, List<Integer>> collect = list.stream().map(integer -> add1(integer)).sorted().collect(Collectors.groupingBy(a -> a + 1));
        System.out.println(collect);
    }

    public static int add1(int num){
        return num + 1;
    }
}
