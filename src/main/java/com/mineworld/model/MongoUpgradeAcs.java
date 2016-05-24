/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.model;


/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public class MongoUpgradeAcs /*extends BaseMongoEntity*/ {
   /* 
    *//***//*
    private static final long serialVersionUID = 5425458520615422405L;
    
    *//***//*
    public static final String COLLECTION_NAME = "UpgradeAcs";
    
    private String u; // 用户账号
    
    private byte[] v;// online;
    
    private boolean r = false;// 是否接收消息回复
    
    *//**
     * 
     *//*
    public MongoUpgradeAcs() {
        
    }
    
    *//**
     * 
     *//*
    public MongoUpgradeAcs(String u, byte[] v) {
        super();
        this.setU(u);
        this.setV(v);
    }
    
    *//**
     * @return 获取 u属性值
     *//*
    public String getU() {
        return u;
    }
    
    *//**
     * @param u 设置 u 属性值为参数值 u
     *//*
    public void setU(String u) {
        this.u = u;
    }
    
    *//**
     * @return 获取 v属性值
     *//*
    public byte[] getV() {
        return v;
    }
    
    *//**
     * @param v 设置 v 属性值为参数值 v
     *//*
    public void setV(byte[] v) {
        this.v = v;
    }
    
    *//**
     * @return 获取 r属性值
     *//*
    public boolean isR() {
        return r;
    }
    
    *//**
     * @param r 设置 r 属性值为参数值 r
     *//*
    public void setR(boolean r) {
        this.r = r;
    }
    
    *//**
     * 
     * @see com.app.entity.BaseMongoEntity#getCollectionName()
     *//*
    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }
    */
}
