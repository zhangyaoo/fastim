package com.zyblue.fastim.sample.constant;

public interface UrlConstant {

    String BASE_URL = "/fastim";

    interface Biz{
        /**
         * b应用下面的msg-handler服务handle方法
         */
        String MSG1  = BASE_URL + "/app-b/msg-handler/handle";

        /**
         * a应用下面的msg-handler服务handle方法
         */
        String MSG2  = BASE_URL + "/app-a/msg-handler/handle";
    }

    interface Gate{
        /**
         * 获取token
         */
        String LOGIN  = BASE_URL + "/login";

        /**
         * 注册
         */
        String REGISTER  = BASE_URL + "/register";

        /**
         * 获取token
         */
        String LOGOUT  = BASE_URL + "/logout";
    }

    interface Leaf{
        /**
         * 获取ID
         */
        String GET_DISTRIBUTE_ID  = BASE_URL + "/getDistributeId";
    }
}
