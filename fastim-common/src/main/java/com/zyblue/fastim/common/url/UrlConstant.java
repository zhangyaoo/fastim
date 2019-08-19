package com.zyblue.fastim.common.url;

public interface UrlConstant {

    String BASE_URL = "/fastim";

    interface Gate{
        /**
         * 获取服务器信息
         */
        String GET_SERVERINFO  = BASE_URL + "/getServerInfo";
    }

    interface Leaf{
        /**
         * 获取ID
         */
        String GET_DISTRIBUTE_ID  = BASE_URL + "/getDistributeId";
    }
}
