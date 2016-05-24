/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.utils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.List;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public final class Constant {
    
    private Constant() {
        
    }
    
    /***/
    public static AttributeKey<String> KEY_USER_ACCOUNT = AttributeKey.newInstance("USER_ACCOUNT");
    
    /***/
    public static AttributeKey<String> KEY_TER_ACCOUNT = AttributeKey.newInstance("TER_ACCOUNT");
    
    /*add by xujian*/
    public static AttributeKey<String> KEY_EXIST = AttributeKey.newInstance("EXIST_ACCOUNT");
    
    /***/
    public static AttributeKey<List<Channel>> KEY_SUB_CHANNELS = AttributeKey.newInstance("SUB_CHANNELS");
    
    /***/
    public static AttributeKey<Byte> KEY_CHANNEL_TYPE = AttributeKey.newInstance("CHANNEL_TYPE");
    
    /***/
    public static AttributeKey<String> KEY_ZLL_VER = AttributeKey.newInstance("ZLL_VER");
    
    /***/
    public static AttributeKey<String> KEY_ACS_VER = AttributeKey.newInstance("ACS_VER");
    
    /***/
    public static final short MSG_TYPE_SERVER = 0x0a;
    
    /***/
    public static final short MSG_TYPE_UP_DATA = 0x0b;
    
    /***/
    public static final short MSG_TYPE_CONTROL = 0x0c;
    
    /***/
    public static final short MSG_TYPE_HEART_BEAT = 0x1e;
    
    /***/
    public static final short MSG_TYPE_HEART_BEAT_REPLY = 0x1f;
    
    /***/
    public static final short MSG_TYPE_LOGIN = 0x50;
    
    /***/
    public static final short MSG_TYPE_LOGIN_REPLY = 0x51;
    
    /** 重启网关 **/
    public static final short MSG_TYPE_RESTART = 0x01;
    
    /** 重启网关回复 **/
    public static final short MSG_TYPE_RESTART_REPLY = 0x02;
    
    /** 查询网关信息 **/
    public static final short MSG_TYPE_GET_INFO = 0x07;
    
    /** 查询网关信息回复 **/
    public static final short MSG_TYPE_GET_INFO_REPLY = 0x08;
    
    /** 升级Zll **/
    public static final short MSG_TYPE_UPGRADE_ZLL = 0x03;
    
    /** 升级Zll回复 **/
    public static final short MSG_TYPE_UPGRADE_ZLL_REPLY = 0x04;
    
    /** 升级ACS **/
    public static final short MSG_TYPE_UPGRADE_ACS = 0x05;
    
    /** 升级ACS回复 **/
    public static final short MSG_TYPE_UPGRADE_ACS_REPLY = 0x062;
    
    public static final Byte TER_CHANNEL = 1;
    
    public static final Byte APP_CHANNEL = 2;
    
}
