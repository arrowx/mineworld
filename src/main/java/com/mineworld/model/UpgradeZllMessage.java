/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.model;

import com.mineworld.utils.ByteUtils;
import com.mineworld.utils.Constant;
import com.mineworld.utils.ShortUtils;

/**
 * 升级网关的消息
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2016年1月6日 xuqingqing
 */
public class UpgradeZllMessage extends BaseMessage {
    
    private byte[] version;
    
    private short size;
    
    private String md5;
    
    private String uri;
    
    /**
     * @param b
     * @param t
     */
    public UpgradeZllMessage(byte[] b) {
        this(b, 0);
        this.version = new byte[] { getContent()[0], getContent()[1], getContent()[2] };
        this.size = ShortUtils.bytesToShort(new byte[] { getContent()[3], getContent()[4] });
        this.md5 = new String(getContent(), 5, 32);
        this.uri = new String(getContent(), 37, getLength() - 40);
    }
    
    /**
     * @param b
     * @param t
     */
    public UpgradeZllMessage(byte[] v, short s, String md5, String uri) {
        super(ByteUtils.getMergeBytes(v, ShortUtils.shortToBytes(s), md5.getBytes(), uri.getBytes()), CON_TYPE);
        this.version = v;
        this.size = s;
        this.md5 = md5;
        this.uri = uri;
    }
    
    /**
     * @return 获取 version属性值
     */
    public byte[] getVersion() {
        return version;
    }
    
    /**
     * @return 获取 size属性值
     */
    public short getSize() {
        return size;
    }
    
    /**
     * @return 获取 uri属性值
     */
    public String getUri() {
        return uri;
    }
    
    /**
     * @return 获取 md5属性值
     */
    public String getMd5() {
        return md5;
    }
    
    /**
     * @param b
     * @param t
     */
    private UpgradeZllMessage(byte[] b, int t) {
        super(b, t);
    }
    
    /**
     * 
     * @see com.app.terminal.model.BaseMessage#getType()
     */
    @Override
    public byte getType() {
        return Constant.MSG_TYPE_UPGRADE_ZLL;
    }
    
}
