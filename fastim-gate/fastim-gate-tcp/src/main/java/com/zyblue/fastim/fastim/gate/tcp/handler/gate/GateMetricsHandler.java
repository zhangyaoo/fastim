package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author will
 * @date 2021/7/24 17:20
 *
 * TODO 执行线程数、队列任务数、ByteBuf使用堆内存数和堆外内存数
 */
@ChannelHandler.Sharable
public class GateMetricsHandler extends ChannelDuplexHandler {
    /**
     * 链接统计
     */
    private final LongAdder totalConnectionNumber = new LongAdder();
    /**
     * 异常统计
     */
    private final LongAdder totalExceptionNumber = new LongAdder();


    {
        MetricRegistry metricRegistry = new MetricRegistry();
        metricRegistry.register("totalConnectionNumber", (Gauge<Long>) totalConnectionNumber::longValue);
        metricRegistry.register("totalExceptionNumber", (Gauge<Long>) totalExceptionNumber::longValue);

        ConsoleReporter consoleReporter =  ConsoleReporter.forRegistry(metricRegistry).build();
        consoleReporter.start(5, TimeUnit.SECONDS);

        JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
        jmxReporter.start();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        totalExceptionNumber.increment();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNumber.increment();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNumber.decrement();
        super.channelInactive(ctx);
    }
}
