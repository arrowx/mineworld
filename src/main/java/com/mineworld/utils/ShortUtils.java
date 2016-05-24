/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.utils;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public final class ShortUtils {
    
    private ShortUtils() {
        
    }
    
    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
     * 
     * @param value
     *            要转换的int值
     * @return byte数组
     */
    public static byte[] shortToBytes(short value) {
        byte[] src = new byte[2];
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }
    
    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序。
     * 
     * @param ary
     *            byte数组
     * @param offset
     *            从数组的第offset位开始
     * @return int数值
     */
    public static short bytesToShort(byte[] ary) {
        return bytesToShort(ary, 0);
    }
    
    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序。
     * 
     * @param ary
     *            byte数组
     * @param offset
     *            从数组的第offset位开始
     * @return int数值
     */
    public static short bytesToShort(byte[] ary, int offset) {
        short value;
        value = (short) ((ary[offset] & 0xFF) | ((ary[offset + 1] << 8) & 0xFF00));
        return value;
    }
}
