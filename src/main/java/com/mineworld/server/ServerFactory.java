/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.server;


/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月22日 xuqingqing
 */
public class ServerFactory {
    
    private static Server server;
    
    public static final Server getServer() {
        if (server != null) {
            return server;
        }
        
        /*if (EPlatform.Windows.equals(OSinfo.getOSname())) {
            server = DefaultTcpServer.getInstance();
        } else {
            server = EpollTcpServer.getInstance();
        }*/
        return server;
    }
}
