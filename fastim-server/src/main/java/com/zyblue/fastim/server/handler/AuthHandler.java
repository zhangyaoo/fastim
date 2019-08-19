package com.zyblue.fastim.server.handler;

import com.zyblue.fastim.common.constant.ChannelAttr;
import com.zyblue.fastim.common.redis.RedisKey;
import com.zyblue.fastim.server.config.ZKConfig;
import com.zyblue.fastim.server.util.LoginUtil;
import com.zyblue.fastim.server.util.SpringBeanFactory;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;


public class AuthHanlder extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(AuthHanlder.class);

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive");
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
        /*String token = stringRedisTemplate.opsForValue().get(RedisKey.FASTIM_TOKEN + 9527);
        logger.info("token:{}", token);
        //Attribute<String> attr = channelHandlerContext.channel().attr(AttributeKey.newInstance(ChannelAttr.TOKEN.getVal()));
        if(token != null){
            // 只要连接未断开，就可以一直登陆着，不需要鉴权
            //channelHandlerContext.pipeline().remove(this);
            super.channelRead(channelHandlerContext, o);
        }else {
            logger.info("token is null, close channel");
            channelHandlerContext.channel().close();
        }*/

        super.channelRead(channelHandlerContext, o);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("handlerRemoved begin");
        super.handlerRemoved(ctx);
        /*if (LoginUtil.hasLogin(ctx.channel())) {
            logger.info("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            logger.info("无登录验证，强制关闭连接!");
        }*/
    }
}
