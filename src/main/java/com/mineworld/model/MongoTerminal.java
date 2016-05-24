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
public class MongoTerminal/* extends BaseMongoEntity */{
    /*
    *//***//*
    public static final String COLLECTION_NAME = "Terminal";
    
    private String u; // 用户账号
    
    private String p; // 网关账号
    
    private boolean ol;// online;
    
    private String zv;
    
    private String av;
    
    private String sn;
    
    private String ip;
    
    private String remark;
    
    *//**
     * 
     *//*
    public MongoTerminal() {
        super();
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
     * @return 获取 p属性值
     *//*
    public String getP() {
        return p;
    }
    
    *//**
     * @return 获取 ol属性值
     *//*
    public boolean isOl() {
        return ol;
    }
    
    *//**
     * @param ol 设置 ol 属性值为参数值 ol
     *//*
    public void setOl(boolean ol) {
        this.ol = ol;
    }
    
    *//**
     * @return 获取 zv属性值
     *//*
    public String getZv() {
        return zv;
    }
    
    *//**
     * @param zv 设置 zv 属性值为参数值 zv
     *//*
    public void setZv(String zv) {
        this.zv = zv;
    }
    
    *//**
     * @return 获取 av属性值
     *//*
    public String getAv() {
        return av;
    }
    
    *//**
     * @param av 设置 av 属性值为参数值 av
     *//*
    public void setAv(String av) {
        this.av = av;
    }
    
    *//**
     * @param p 设置 p 属性值为参数值 p
     *//*
    public void setP(String p) {
        this.p = p;
    }
    
    *//**
     * @return 获取 sn属性值
     *//*
    public String getSn() {
        return sn;
    }
    
    *//**
     * @param sn 设置 sn 属性值为参数值 sn
     *//*
    public void setSn(String sn) {
        this.sn = sn;
    }
    
    *//**
     * @return 获取 ip属性值
     *//*
    public String getIp() {
        return ip;
    }
    
    *//**
     * @param ip 设置 ip 属性值为参数值 ip
     *//*
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    *//**
     * @return 获取 remark属性值
     *//*
    public String getRemark() {
        return remark;
    }
    
    *//**
     * @param remark 设置 remark 属性值为参数值 remark
     *//*
    public void setRemark(String remark) {
        this.remark = remark;
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
