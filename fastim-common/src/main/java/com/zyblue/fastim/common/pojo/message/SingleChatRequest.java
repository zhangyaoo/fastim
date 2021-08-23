package com.zyblue.fastim.common.pojo.message;

import java.time.LocalDateTime;

/**
 * @author will
 * @date 2021/7/19 17:06
 */
public class SingleChatRequest  implements MsgRequest {
    private Long fromUid;

    private Long toUid;

    private String content;

    private byte contentType;

    private LocalDateTime sendTime;
}
