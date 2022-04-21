package com.zyblue.fastim.logic.msg.manager;

import com.zyblue.fastim.logic.msg.client.annotation.MsgReceivedListener;
import com.zyblue.fastim.logic.msg.client.worker.MsgClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author will
 * @date 2022/4/14 11:52
 */
@Component
public class MsgManager {

    @Resource
    private MsgClient msgClient;

    @MsgReceivedListener
    public void received(List<Map<String, Object>> data){

    }

    public void sendMsg(Long userId){
        //msgClient.send();
    }

    public void sendMsgGroup(){
        //msgClient.sendGroup();
    }
}
