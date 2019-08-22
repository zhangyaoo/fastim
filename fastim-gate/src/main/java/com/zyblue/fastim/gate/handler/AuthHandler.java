package com.zyblue.fastim.gate.handler;

import com.zyblue.fastim.common.redis.RedisKey;
import com.zyblue.fastim.gate.util.SpringBeanFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;


public class AuthHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("AuthHandler channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("channelRead");
        StringRedisTemplate stringRedisTemplate = SpringBeanFactory.getBean(StringRedisTemplate.class);
        String token = stringRedisTemplate.opsForValue().get(RedisKey.FASTIM_TOKEN + 9527);
        logger.info("token:{}", token);

        if(null == token){
            channelHandlerContext.channel().close();
        }else {
            super.channelRead(channelHandlerContext, o);
        }
        super.channelRead(channelHandlerContext, o);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("handlerRemoved begin");
        super.handlerRemoved(ctx);
    }
}
