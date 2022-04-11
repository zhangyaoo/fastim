package com.zyblue.fastim.client.msg;

import com.google.common.eventbus.Subscribe;
import com.zyblue.fastim.client.event.NewMsgEvent;
import com.zyblue.fastim.common.pojo.message.MsgRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author will
 * @date 2021/12/3 16:13
 */
public class MsgSubscribeManager {
    private final static Logger logger = LoggerFactory.getLogger(MsgSubscribeManager.class);

    @Subscribe
    public void recMsg(NewMsgEvent event){
        MsgRequest data = event.getData();
        logger.info("recMsg data:{}", data);
    }
}
