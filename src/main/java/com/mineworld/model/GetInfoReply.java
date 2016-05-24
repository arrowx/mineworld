/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.model;

import com.mineworld.utils.ByteUtils;
import com.mineworld.utils.Constant;

/**
 * 升级网关的消息
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2016年1月6日 xuqingqing
 */
public class GetInfoReply extends BaseMessage {
    
    private final byte[] zllVersion;
    
    private final byte[] acsVersion;
    
    private final String snid;
    
    /**
     * @param b
     * @param t
     */
    public GetInfoReply(byte[] b, int t) {
        super(b, t);
        this.zllVersion = ByteUtils.cutOutByte(getContent(), 3);
        this.acsVersion = ByteUtils.cutOutByte(getContent(), 3, 3);
        this.snid = new String(getContent(), 6, getLength() - 9);
    }
    
    /**
     * @param b
     * @param t
     */
    public GetInfoReply(byte[] b) {
        this(b, 0);
    }
    
    /**
     * @return 获取 zllVersion属性值
     */
    public byte[] getZllVersion() {
        return zllVersion;
    }
    
    /**
     * @return 获取 acsVersion属性值
     */
    public byte[] getAcsVersion() {
        return acsVersion;
    }
    
    /**
     * @return 获取 snid属性值
     */
    public String getSnid() {
        return snid;
    }
    
    /**
     * 
     * @see com.app.terminal.model.BaseMessage#getType()
     */
    @Override
    public byte getType() {
        return Constant.MSG_TYPE_GET_INFO_REPLY;
    }
    
}
