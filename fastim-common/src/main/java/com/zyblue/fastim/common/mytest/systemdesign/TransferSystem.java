package com.zyblue.fastim.common.mytest.systemdesign;

import java.math.BigDecimal;

/**
 * @author will
 * @date 2021/8/24 15:23
 * 转账系统设计，转账接口设计 订单
 * 考察事务，分布式事务，锁，死锁，幂等
 * 要求：保证高并发，高可靠性，在单库和分库情况下分不同实现
 *
 * 高并发下：可以利用MQ来做
 * 1、先发半事务消息
 * 2、后插入状态为0的订单数据
 * 3、提交办事务消息，提交成功则表明成功
 */
public class TransferSystem {


    public static void transfer(int fromAccountId, int toAccountId, BigDecimal amount){
        /*
         * 校验系统，防止任意转账
         */
        if(validate(fromAccountId, toAccountId, amount)){
            /*
             * 订单系统
             */
            Long orderNo = orderHandle(fromAccountId, toAccountId, amount);

            /*
             * 账户系统
             */
            Boolean result = accountHandle(orderNo, fromAccountId, toAccountId, amount);
        }
    }

    public static Boolean validate(int fromAccountId, int toAccountId, BigDecimal amount){
        // 防刷、防重、验签
        return true;
    }

    public static Long orderHandle(int fromAccountId, int toAccountId, BigDecimal amount){
        // 生成订单号
        // 校验fromAccountId余额是否够
        // 生成status为0状态的订单,插入数据库
        return 0L;
    }

    public static Boolean accountHandle(Long orderNo, int fromAccountId, int toAccountId, BigDecimal amount){
        //1、幂等校验
        //2、顺序排序锁定账户避免死锁。可以利用for update或者行锁或者
        //3、查询账户金额，进行校验
        //4、事务开启：插入转账流水，减少锁的持有时间
        //5、update语句带上where条件，更新失败。更新失败重新在查余额再更新或者直接返回失败
        //6、更新订单状态，事务提交结束
        return false;
    }
}
