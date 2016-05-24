/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mineworld.model.BaseMessage;



/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public class MessageEncoder extends MessageToByteEncoder<BaseMessage> {
    
    protected static Logger logger = LoggerFactory.getLogger(MessageEncoder.class);
    
    @Override
    protected void encode(ChannelHandlerContext context, BaseMessage msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.getMessageByByte());
        if (logger.isDebugEnabled()) {
            logger.debug("send:" + ByteBufUtil.hexDump(msg.getMessageByByte()));
        }
    }
    
}
