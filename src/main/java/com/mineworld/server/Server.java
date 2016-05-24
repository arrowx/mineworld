/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.Collection;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月22日 xuqingqing
 */
public interface Server {
    
    void startService(String ip, int port);
    
    Server getServer();
    
    Channel find(ChannelId id);
    
    void addChannel(Channel channel);
    
    void removeChannel(Channel channel);
    
    void removeAllChannel(Collection<?> c);
    
    boolean contains();
}
