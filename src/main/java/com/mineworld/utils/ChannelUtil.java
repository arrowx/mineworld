/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.List;

import com.mineworld.cache.ChannelIdCache;
import com.mineworld.event.spring.EventUtil;
import com.mineworld.model.ChannelReadOutEvent;
import com.mineworld.server.ServerFactory;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年11月27日 xuqingqing
 */
public abstract class ChannelUtil {
    
    /**
     * @param channel
     * @param util
     */
    public static final void removeChannel(Channel channel, EventUtil util) {
        ServerFactory.getServer().removeChannel(channel);
        ChannelIdCache cache = ChannelIdCache.getInstance();
        String acct = channel.attr(Constant.KEY_TER_ACCOUNT).get();
        if (acct != null) {
            cache.remove(acct, channel.id());
            if (util != null)
                util.postAsyncEvent(new ChannelReadOutEvent(acct, channel));
        }
        channel.close();
    }
    
    /**
     * 检测是否知道通道是网关还是手机app端
     * 
     * @param channel
     * @return
     */
    public static final byte getChannelType(Channel channel) {
        Byte tmp = channel.attr(Constant.KEY_CHANNEL_TYPE).get();
        if (tmp == null)
            return 0;
        return tmp.byteValue();
    }
    
    /**
     * 检测网关是否在线
     * 
     * @param acct
     * @return
     */
    public static final boolean checkGatewayIsOnline(String acct) {
        ChannelIdCache c = ChannelIdCache.getInstance();
        List<ChannelId> cids = c.get(acct);
        if (cids == null) {
            return false;
        }
        
        for (ChannelId cid : cids) {
            Channel subChannel = ServerFactory.getServer().find(cid);
            if (subChannel == null) {
                continue;
            }
            byte tmpCType = ChannelUtil.getChannelType(subChannel);
            if (tmpCType == 0 || tmpCType == Constant.TER_CHANNEL) {
                return true;
            }
        }
        
        return false;
    }
}
