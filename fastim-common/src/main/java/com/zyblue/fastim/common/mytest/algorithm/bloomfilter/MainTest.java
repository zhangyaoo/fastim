package com.zyblue.fastim.common.mytest.algorithm.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class MainTest {

    private static BloomFilter<Integer> bf =  BloomFilter.create(Funnels.integerFunnel(), 1000000, 0.001);

    public static void main(String[] args) {
        for(int i=0;i<1000000;i++){
            bf.put(i);
        }

        // 匹配已在过滤器中的值，是否有匹配不上的
        for (int i = 0; i < 1000000; i++) {
            if (!bf.mightContain(i)) {
                System.out.println("有坏人逃脱了~~~");
            }
        }
    }
}
