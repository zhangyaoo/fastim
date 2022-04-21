package com.zyblue.fastim.common.constant;

/**
 * 消息类型
 *
 * @author will
 */
public class MessageType {

    public interface Gate {

    }

    public interface Client {

    }


    public static final String REQUEST = "REQUEST";

    public static final String ACK = "ACK";

    public static final String SENT_DATA = "SENT_DATA";

    public static final String SENT_METADATA = "SENT_METADATA";

    public static final String COMMIT = "COMMIT";

    public static final String CANCEL = "CANCEL";

}
