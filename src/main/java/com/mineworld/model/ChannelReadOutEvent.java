/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.model;

import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * 通道断线事件
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public class ChannelReadOutEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String acct;
    
    private Channel channel;
    
    /**
     * 
     */
    public ChannelReadOutEvent() {
        super();
    }
    
    /**
     * @param acct
     * @param subChannel
     */
    public ChannelReadOutEvent(String acct, Channel channel) {
        super();
        this.acct = acct;
        this.channel = channel;
    }
    
    /**
     * @return 获取 acct属性值
     */
    public String getAcct() {
        return acct;
    }
    
    /**
     * @param acct 设置 acct 属性值为参数值 acct
     */
    public void setAcct(String acct) {
        this.acct = acct;
    }
    
    /**
     * @return 获取 channel属性值
     */
    public Channel getChannel() {
        return channel;
    }
    
    /**
     * @param channel 设置 channel 属性值为参数值 channel
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    
}
