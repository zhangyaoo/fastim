package com.zyblue.fastim.fastim.gate.http.plugin.chain;

import com.zyblue.fastim.fastim.gate.http.plugin.GatePluginHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author will
 * @date 2021/6/7 23:27
 * 插件处理链条
 */
@Component
public class HttpHandlerChain {

    /**
     * 插件之间不耦合
     */
    private final List<GatePluginHandler> handlers;

    private HttpHandlerContext headContext;


    /**
     * 组装处理链
     */
    public HttpHandlerChain(List<GatePluginHandler> pluginHandlers) {
        this.handlers = refresh(pluginHandlers);
    }

    /**
     * 开始处理
     */
    public void process(ChannelHandlerContext channelHandlerContext, DefaultHttpRequest request){
        headContext.setChannelHandlerContext(channelHandlerContext);
        headContext.execute(request);
    }

    /**
     * 重新排序和过滤
     */
    public List<GatePluginHandler> refresh(List<GatePluginHandler> pluginHandlersNew){
        if(CollectionUtils.isEmpty(pluginHandlersNew)){
            throw new RuntimeException("plugin is empty");
        }
        //List<GatePluginHandler> pluginHandlersNew = pluginHandlers.stream().filter(GatePluginHandler::enable).sorted(Comparator.comparingInt(GatePluginHandler::order)).collect(Collectors.toList());
        for (int i = 0; i < pluginHandlersNew.size(); i++) {
            HttpHandlerContext httpHandlerContext = new HttpHandlerContext(pluginHandlersNew.get(i));
            if(i != pluginHandlersNew.size() - 1){
                httpHandlerContext.setNext(new HttpHandlerContext(pluginHandlersNew.get(i + 1)));
            }
        }
        this.headContext = new HttpHandlerContext(pluginHandlersNew.get(0));
        return pluginHandlersNew;
    }

    public List<GatePluginHandler> getPluginHandlers() {
        return handlers;
    }
}
