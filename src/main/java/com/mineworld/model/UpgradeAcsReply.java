/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.model;

import com.mineworld.utils.Constant;

/**
 * 升级网关的消息回应
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2016年1月6日 xuqingqing
 */
public class UpgradeAcsReply extends BaseMessage {
    
    /**
     * 1：表示成功，2：表示软件太大，3：表示软件名称错误或软件错误
     */
    private static final UpgradeAcsReply OK = new UpgradeAcsReply(new byte[0x01], CON_TYPE);
    
    private static final UpgradeAcsReply FAIL_OVER_SIZE = new UpgradeAcsReply(new byte[0x02], CON_TYPE);
    
    private static final UpgradeAcsReply FAIL_MD5_ERROR = new UpgradeAcsReply(new byte[0x03], CON_TYPE);
    
    private final byte result = 0x01;
    
    /**
     * @param b
     * @param t
     */
    private UpgradeAcsReply(byte[] b, int t) {
        super(b, t);
    }
    
    /**
     * @param b
     * @param t
     */
    private UpgradeAcsReply(byte[] c) {
        super(c, CON_TYPE);
    }
    
    public static final UpgradeAcsReply getInstance(byte r) {
        switch (r) {
            case 0x01:
                return OK;
            case 0x02:
                return FAIL_OVER_SIZE;
            case 0x03:
                return FAIL_MD5_ERROR;
            default:
                return OK;
        }
    }
    
    /**
     * @return 获取 result属性值
     */
    public byte getResult() {
        return result;
    }
    
    /**
     * 
     * @see com.app.terminal.model.BaseMessage#getType()
     */
    @Override
    public byte getType() {
        return Constant.MSG_TYPE_UPGRADE_ZLL_REPLY;
    }
    
}
