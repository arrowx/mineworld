/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author xujian
 * @since 1.0
 * @version 2016年2月29日 xuqingqing
 */
public final class AcsPropertiesConfig {
    
    private static final Properties prop = new Properties();
    
    /**
     * 
     */
    public static void initSystemContext() {
        InputStream in = null;
        try {
            in = AcsPropertiesConfig.class.getResourceAsStream("acsPropertiesConfig.properties");
            prop.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }
    }
    
    /**
     * @param key
     * @return
     * @see java.util.Properties#getProperty(java.lang.String)
     */
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
    
}
