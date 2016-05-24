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
public final class ByteUtils {
    
    private ByteUtils() {
        
    }
    
    /**
     * 合并数组
     * 
     * @param bss
     * @return
     */
    public static byte[] getMergeBytes(byte[]... bss) {
        if (bss == null)
            return null;
        
        int length = 0;
        for (byte[] bs : bss) {
            if (bs != null)
                length += bs.length;
        }
        byte[] ret = new byte[length];
        int index = 0;
        for (byte[] bs : bss) {
            if (bs != null)
                for (byte b : bs) {
                    ret[index++] = b;
                }
        }
        
        return ret;
    }
    
    /**
     * 合并两个byte数组
     * 
     * @param pByteA
     * @param pByteB
     * @return
     */
    public static byte[] getMergeBytes(byte[] pByteA, byte[] pByteB) {
        int aCount = pByteA.length;
        int bCount = pByteB.length;
        byte[] b = new byte[aCount + bCount];
        for (int i = 0; i < aCount; i++) {
            b[i] = pByteA[i];
        }
        for (int i = 0; i < bCount; i++) {
            b[aCount + i] = pByteB[i];
        }
        return b;
    }
    
    /**
     * 截取byte数据
     * 
     * @param b 是byte数组
     * @param j 是大小
     * @return
     */
    public static byte[] cutOutByte(byte[] b, int j) {
        if (b.length == 0 || j == 0) {
            return null;
        }
        byte[] bjq = new byte[j];
        for (int i = 0; i < j; i++) {
            bjq[i] = b[i];
        }
        return bjq;
    }
    
    /**
     * 截取byte数据
     * 
     * @param b 是byte数组
     * @param offset
     * @param length
     * @return
     */
    public static byte[] cutOutByte(byte[] b, int offset, int length) {
        if (b.length == 0 || offset >= b.length || length == 0) {
            return null;
        }
        if (offset + length > b.length) {
            length = b.length - offset;
        }
        byte[] bjq = new byte[length];
        length = offset + length;
        for (int i = offset; i < length; i++) {
            bjq[i - offset] = b[i];
        }
        return bjq;
    }
    
    public static int search(byte[] b, byte key) {
        int ind = 0;
        for (byte temp : b) {
            if (temp == key) {
                break;
            }
            ind++;
        }
        return ind;
    }
    
    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
     * 
     * @param value
     *            要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes(int value, int bitSize) {
        if (bitSize <= 0 || bitSize > 4) {
            bitSize = 4;
        }
        
        byte[] src = new byte[bitSize];
        for (int i = 0; i < bitSize; i++) {
            src[bitSize - i - 1] = (byte) ((value >> (8 * i)) & 0xFF);
        }
        
        return src;
    }
}
