/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月22日 xuqingqing
 */
public abstract class AbstractServer implements Server {
    
    protected static Logger logger = LoggerFactory.getLogger(AbstractServer.class);
    
    /** 用于分配接受连接请求的线程组个数 */
    protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2; // 默认
    
    /** 处理业务线程大小 */
    protected static final int BIZTHREADSIZE = 2 * BIZGROUPSIZE;
    
    private final ChannelGroup allChannels = new DefaultChannelGroup(new DefaultEventLoop());
    
    /** 设置x秒检测chanel是否接受过心跳数据 **/
    //public static final int READ_WAIT_SECONDS = SystemContext.getInstance().getInt("terminal.timeout");
    public static final int READ_WAIT_SECONDS = 60;
    
    protected ServerBootstrap b;
    
    /**
     * 
     * @see com.app.terminal.server.Server#startService(java.lang.String, java.lang.String)
     */
    @Override
    public void startService(final String ip, final int port) {
        if (b != null) {
            logger.info("TCP Service has been started");
            return;
        }
        createDebugThread();
        b = createServer();
        
        if (b == null) {
            logger.error("start TCP sevice create error：");
            return;
        }
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                try {
                    ChannelFuture channelFuture = b.bind(ip, port).sync();
                    logger.error("TCP Service start at:" + ip + ":" + port);
                    channelFuture.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    logger.error("start TCP sevice start error：", e);
                    closeServer();
                }
                
            }
        }).start();
    }
    
    /**
     * 
     * @see com.app.terminal.server.Server#find(io.netty.channel.ChannelId)
     */
    @Override
    public Channel find(ChannelId id) {
        return allChannels.find(id);
    }
    
    /**
     * 
     * @see com.app.terminal.server.Server#addChannel(io.netty.channel.Channel)
     */
    @Override
    public void addChannel(Channel channel) {
        allChannels.add(channel);
    }
    
    /**
     * 
     * @see com.app.terminal.server.Server#removeChannel(io.netty.channel.Channel)
     */
    @Override
    public void removeChannel(Channel channel) {
        allChannels.remove(channel);
    }
    
    /**
     * 
     * @see com.app.terminal.server.Server#removeAllChannel()
     */
    @Override
    public void removeAllChannel(Collection<?> c) {
        allChannels.removeAll(c);
    }
    
    /**
     * 
     * @see com.app.terminal.server.Server#contains()
     */
    @Override
    public boolean contains() {
        // TODO 自动生成方法存根注释，方法实现时请删除此注释
        return false;
    }
    
    public Server getServer() {
        return this;
    }
    
    /**
     * 
     */
    protected abstract ServerBootstrap createServer();
    
    /**
     * 
     */
    protected void closeServer() {
        
    }
    
    private void createDebugThread() {
        Thread s = new Thread() {
            
            @Override
            public void run() {
                int size = -1;
                while (true) {
                    if (size != allChannels.size()) {
                        size = allChannels.size();
                        logger.error("服务器现在一共有" + allChannels.size() + "个网关在线。");
                    }
                    try {
                        Thread.sleep(20 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        s.setDaemon(true);
        s.start();
    }
    
    public void shutdown() {
        allChannels.close().awaitUninterruptibly();
    }
    
}
