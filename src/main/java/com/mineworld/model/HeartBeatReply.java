/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.model;

import com.mineworld.utils.Constant;

/**
 * 升级网关的消息
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2016年1月6日 xuqingqing
 */
public class HeartBeatReply extends BaseMessage {
    
    public static final HeartBeatReply instance = new HeartBeatReply(null, CON_TYPE);
    
    /**
     * @param b
     * @param t
     */
    private HeartBeatReply(byte[] b, int t) {
        super(b, t);
    }
    
    /**
     * 
     * @see com.app.terminal.model.BaseMessage#getType()
     */
    @Override
    public byte getType() {
        return Constant.MSG_TYPE_HEART_BEAT_REPLY;
    }
    
}
