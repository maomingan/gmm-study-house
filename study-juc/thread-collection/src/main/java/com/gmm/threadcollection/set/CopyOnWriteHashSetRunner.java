package com.gmm.threadcollection.set;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWriteArraySet是相对于HashSet的，它是线程安全的
 */
public class CopyOnWriteHashSetRunner {

    public static void main(String[] args) {

        CopyOnWriteArraySet<Integer> arraySet = new CopyOnWriteArraySet<>();
        int i = new Random().nextInt();
        arraySet.add(i);

        System.out.println(arraySet.contains(i));

    }

}
