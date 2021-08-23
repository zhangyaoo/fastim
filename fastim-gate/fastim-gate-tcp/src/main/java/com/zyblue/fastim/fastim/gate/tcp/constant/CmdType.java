package com.zyblue.fastim.fastim.gate.tcp.constant;


/**
 * @author will
 * @date 2021/7/12 17:45
 * 心跳、推送、单聊、群聊
 */
public enum CmdType {
    SINGLE_CHAT((byte) 1, "单聊", "SingleChatService", "handle"),
    GROUP_CHAT((byte) 2, "群聊", "GroupChatService", "handle"),
    HEARTBEAT((byte) 3, "心跳",  "HeartbeatService", "handle"),
    PUSH((byte) 4, "推送",  "PushService", "handle"),
    SYSTEM_NOTIFY((byte) 5, "系统消息",  "SystemNotifyService", "handle"),
    CUSTOMER((byte) 6, "客服",  "CustomerService", "handle"),
    WEB_ROOM((byte) 7, "聊天室",  "WebRoomService", "handle");


    CmdType(byte val, String desc, String mapService, String mapMethod) {
        this.val = val;
        this.desc = desc;
        this.mapService = mapService;
        this.mapMethod = mapMethod;
    }

    public static CmdType getCmdTypeByVal(byte val){
        for (CmdType cmdType : CmdType.values()) {
            if (val == cmdType.getVal()) {
                return cmdType;
            }
        }
        return null;
    }

    private byte val;

    private String desc;

    private String mapService;

    private String mapMethod;

    public byte getVal() {
        return val;
    }

    public void setVal(byte val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMapService() {
        return mapService;
    }

    public void setMapService(String mapService) {
        this.mapService = mapService;
    }

    public String getMapMethod() {
        return mapMethod;
    }

    public void setMapMethod(String mapMethod) {
        this.mapMethod = mapMethod;
    }
}
