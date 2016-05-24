/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import com.mineworld.handler.AcsTcpServerHandler;
import com.mineworld.handler.MessageDecoder;
import com.mineworld.handler.MessageEncoder;
import com.mineworld.utils.AcsPropertiesConfig;


/**
 * 
 * @author xujian
 * @since 1.0
 * @version 2016年5月22日 xujian
 */
public class AcsTcpServer extends AbstractServer {
    
    private static AcsTcpServer server;
    
    private AcsTcpServer() {
//        AcsPropertiesConfig.initSystemContext();
    }
    
    /**
     * @return
     */
    public static final synchronized AcsTcpServer getInstance() {
        if (server == null) {
            server = new AcsTcpServer();
        }
        
        return server;
    }
    
    /*
     * NioEventLoopGroup实际上就是个线程池,
     * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件,
     * 每一个NioEventLoop负责处理m个Channel,
     * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE, new PriorityThreadFactory("@+监听连接线程",
        Thread.NORM_PRIORITY));
    
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE, new PriorityThreadFactory(
        "@+I/O线程", Thread.NORM_PRIORITY));
    
    /**
     * 
     * @see com.app.terminal.server.AbstractServer#createServer()
     */
    @Override
    protected ServerBootstrap createServer() {
        
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.option(ChannelOption.TCP_NODELAY, true);
        b.option(ChannelOption.SO_SNDBUF, 1024 * 32);
        b.option(ChannelOption.SO_RCVBUF, 1024 * 32);
        b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        
        //final AcsTcpServerHandler h = SystemInitor.getInstance().getAppContext().getBean(AcsTcpServerHandler.class);
        final AcsTcpServerHandler h = new AcsTcpServerHandler();
        h.setServer(AcsTcpServer.getInstance());
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                
                pipeline.addLast("idlehandler", new IdleStateHandler(READ_WAIT_SECONDS, 0, 0, TimeUnit.SECONDS));
                pipeline.addLast("bmDecoder", new MessageDecoder());
                pipeline.addLast("bmEncoder", new MessageEncoder());
                pipeline.addLast(h);
                // pipeline.addLast(new TcpServerHandler());
            }
        });
        
        return b;
        
    }
    
    /**
     * 
     */
    @Override
    protected void closeServer() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        super.closeServer();
    }
    
}
