package com.zyblue.fastim.client.nettysourcecode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class NettyWork {
    //线程池
    public final Executor executor;
    //boss选择器
    protected Selector selector;
    //原子变量，主要是用来保护线程安全。当本线程执行的时候，排除其他线程的执行
    protected final AtomicBoolean wakenUp = new AtomicBoolean();
    //队列，线程安全队列。
    public final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

    //初始化
    public NettyWork(Executor executor) {
        this.executor = executor;
        try {
            //每一个work也需要一个选择器用来管理通道
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //从线程池中获取一个线程开始执行
        executor.execute(() -> {
            while (true) {
                try {
                    //阻塞状态排除问题
                    wakenUp.set(false);
                    //阻塞
                    selector.select();
                    //处理work任务
                    while (true) {
                        final Runnable task = taskQueue.poll();
                        if (task == null) {
                            break;
                        }
                        //存在work任务开始执行
                        task.run();
                    }
                    //处理任务
                    this.process(selector);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void process(Selector selector) throws IOException {
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        if (selectedKeys.isEmpty()) {
            return;
        }
        Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
        while (ite.hasNext()) {
            SelectionKey key = (SelectionKey) ite.next();
            // 移除，防止重复处理
            ite.remove();
            // 得到事件发生的Socket通道
            SocketChannel channel = (SocketChannel) key.channel();
            // 数据总长度
            int ret = 0;
            boolean failure = true;
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //读取数据
            try {
                ret = channel.read(buffer);
                failure = false;
            } catch (Exception e) {
                // ignore
            }
            //判断是否连接已断开
            if (ret <= 0 || failure) {
                key.cancel();
                System.out.println("客户端断开连接");
            }else{
                System.out.println("收到数据:" + new String(buffer.array()));
                //回写数据
                ByteBuffer outBuffer = ByteBuffer.wrap("收到\n".getBytes());
                channel.write(outBuffer);// 将消息回送给客户端
            }
        }
    }
}
