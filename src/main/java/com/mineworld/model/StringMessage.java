/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月23日 xuqingqing
 */
public abstract class StringMessage extends BaseMessage {
    
    /***/
    private static final long serialVersionUID = -4762393067968243729L;
    
    private String msg;
    
    /**
     * @param content
     */
    protected StringMessage(byte[] content) {
        this(content, CON_TYPE);
    }
    
    /**
     * @param content
     */
    protected StringMessage(String content) {
        this(content.getBytes(), CON_TYPE);
        this.msg = content;
    }
    
    /**
     * @param loginContent
     * @param conType
     */
    protected StringMessage(byte[] content, int conType) {
        super(content, conType);
    }
    
    public String getMsg() {
        if (StringUtils.isNotBlank(msg)) {
            return msg;
        }
        return new String(getContent());
    }
    
    /**
     * 
     * @see com.app.terminal.model.BaseMessage#toString()
     */
    @Override
    public String toString() {
        return this.getClass() + ":" + this.getMsg();
    }
    
}
