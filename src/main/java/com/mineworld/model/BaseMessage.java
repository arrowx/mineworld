/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.model;

import io.netty.buffer.ByteBufUtil;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mineworld.utils.ByteUtils;
import com.mineworld.utils.ShortUtils;


/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public abstract class BaseMessage implements Serializable {
    
    /***/
    private static final long serialVersionUID = -7578987131251764796L;
    
    protected static Logger logger = LoggerFactory.getLogger(BaseMessage.class);
    
    /***/
    protected static final int CON_TYPE = 1;
    
    /***/
    public static final int BYTE_MESSAGE_TYPE = 2;
    
    protected byte[] content;
    
    protected byte[] byteMessage;// 整个消息的byte数组
    
    private int length;
    
    protected BaseMessage(byte[] b, int t) {
        if (t == CON_TYPE) {
            setContent(b);
        } else {
            setByteMessage(b);
        }
    }
    
    /**
     * @return 获取 length属性值
     */
    public short getLength() {
        return (short) this.length;
    };
    
    /**
     * @param byteMessage 设置 byteMessage 属性值为参数值 byteMessage
     */
    private void setByteMessage(byte[] byteMessage) {
        this.byteMessage = byteMessage;
        
        this.length = byteMessage.length;
        if (this.length > 3) {
            content = ByteUtils.cutOutByte(byteMessage, 3, byteMessage.length - 3);
        } else {
            content = new byte[] {};
        }
    }
    
    private void setContent(byte[] content) {
        if (content == null) {
            content = new byte[] {};
        }
        this.content = content;
        this.length = content.length + 3;
    };
    
    public byte[] getMessageByByte() {
        if (byteMessage != null && byteMessage.length > 0)
            return byteMessage;
        byte[] length = ShortUtils.shortToBytes(getLength());
        byte type = getType();
        return ByteUtils.getMergeBytes(new byte[] { length[0], length[1], type }, getContent());
    }
    
    public byte[] getContent() {
        return content;
    }
    
    /**
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getClass() + ":" + ByteBufUtil.hexDump(getMessageByByte());
    }
    
    /**
     * @return 获取 length属性值
     */
    public abstract byte getType();
}
