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
public class LoginReply extends StringMessage {
    
    private String acct;
    
    private String pass;
    
    /**
     * @param b
     * @param t
     */
    public LoginReply(byte[] b, int t) {
        super(b, t);
        String[] temp = getMsg().split(" ");
        if (temp.length >= 2) {
            acct = temp[0];
            pass = temp[1];
        }
    }
    
    /**
     * @param b
     * @param t
     */
    public LoginReply(byte[] b) {
        this(b, 0);
    }
    
    /**
     * @param b
     * @param t
     */
    public LoginReply(String msg) {
        super(msg);
    }
    
    /**
     * @return 获取 acct属性值
     */
    public String getAcct() {
        return acct;
    }
    
    /**
     * @return 获取 pass属性值
     */
    public String getPass() {
        return pass;
    }
    
    /**
     * 
     * @see com.app.terminal.model.BaseMessage#getType()
     */
    @Override
    public byte getType() {
        return Constant.MSG_TYPE_LOGIN_REPLY;
    }
    
}
