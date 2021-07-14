package com.zyblue.fastim.common.constant;

/**
 * @author will
 * @date 2021/7/6 16:12
 */
public interface CommonConstant {

     interface Protocol {
          /**
           * 协议头标识
           */
          byte HEAD_DATA = 0X35;
     }

     interface Redis {
          /**
           * 生成分布式ID消息的机器ID
           */
          String IM_LEAF_WORKER_ID_PREFIX = "im_leaf_worker_id_prefix";
     }
}
