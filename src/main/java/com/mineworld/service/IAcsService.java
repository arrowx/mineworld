/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.service;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public interface IAcsService {
    
    /**
     * 重启网关
     * 
     * @param accts
     * @return
     */
    boolean restartGateways(String accts);
    
    /**
     * 升级zll网关
     * 
     * @param accts
     * @param version
     * @param size
     * @param md5
     * @param uri
     * @return
     */
    boolean upgradeZlls(String accts, byte[] version, short size, String md5, String uri);
    
    /**
     * 升级acs网关
     * 
     * @param accts
     * @param version
     * @param size
     * @param md5
     * @param uri
     * @return
     */
    boolean upgradeAcss(String accts, byte[] version, short size, String md5, String uri);
    
}
