package com.zyblue.fastim.common.mytest.algorithm.enterprise;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 实现Set的常数级别时间复杂度的insert 和  remove
 * getRandom函数，要【随机】返回一个数值
 * @author zhangy75
 *
 * 需求：
 * bool insert(int val) 当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
 * bool remove(int val) 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
 * int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
 * 你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1) 。
 */
public class RandomSet {
    private final Map<Integer, Integer> dict = Maps.newHashMap();
    private final List<Integer> list = Lists.newArrayList();
    private final Random random = new Random();

    public boolean insert(Integer val) {
        if(dict.containsKey(val)){
            return false;
        }else {
            // list.add是常数级别的操作，是add到数组末尾
            list.add(val);
            Integer index = list.size() - 1;
            // dict.put是常数级别的操作
            dict.put(val, index);
            return true;
        }
    }

    /**
     * 删除任意索引元素需要【线性时间】，这里的解决方案是总是删除最后一个元素。
     * 1、将要删除元素和最后一个元素交换。
     * 2、将最后一个元素删除。
     * 为此，必须在常数时间获取到要删除元素的索引，因此需要一个哈希表来存储值到索引的映射。
     */
    public boolean remove(Integer val) {
        if(dict.containsKey(val)){
            // dict.remove是常数级别的操作
            Integer index = dict.remove(val);
            // list.remove(int index)不是常数级别的操作，涉及到数组的向前拷贝，需要优化
            int size = list.size();
            Integer lastVal = list.get(size - 1);
            // 交换最后一个元素至val的index
            list.set(index, lastVal);
            dict.put(lastVal, index);
            // 删除最后一个元素
            list.remove(list.size() - 1);
            return true;
        }else {
            return false;
        }
    }

    public int getRandom() {
        int i = random.nextInt(list.size() - 1);
        return list.get(i);
    }
}
