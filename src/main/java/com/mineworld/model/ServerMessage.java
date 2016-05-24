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
public class ServerMessage extends StringMessage {
    
    public static final ServerMessage LOGIN_OK = new ServerMessage("Login Ok!");
    
    public static final ServerMessage LOGIN_FAIL = new ServerMessage("User name or Pass Error!");
    
    public static final ServerMessage  USER_LOGINED = new ServerMessage("User is logged in!");
    
    /**
     * @param b
     * @param t
     */
    public ServerMessage(byte[] b, int t) {
        super(b, t);
    }
    
    /**
     * @param b
     * @param t
     */
    public ServerMessage(byte[] b) {
        this(b, 0);
    }
    
    /**
     * @param b
     * @param t
     */
    public ServerMessage(String msg) {
        super(msg);
    }
    
    /**
     * 
     * @see com.app.terminal.model.BaseMessage#getType()
     */
    @Override
    public byte getType() {
        return Constant.MSG_TYPE_SERVER;
    }
}
