/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.utils;

import java.io.Serializable;
import net.sf.json.JSONObject;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2016年2月29日 xuqingqing
 */
public final class IpLookupUtil {
    
    /**
     * 根据IP地址获取城市
     * 
     * @param ip
     * @return
     */
    public static String lookupIpCity(String ip) {
        IpInfo info = lookupIp(ip);
        if (info != null) {
            return info.getCity();
        }
        return "";
    }
    
    /**
     * 根据IP地址获取城省份
     * 
     * @param ip
     * @return
     */
    public static String lookupIpRegion(String ip) {
        IpInfo info = lookupIp(ip);
        if (info != null) {
            return info.getRegion();
        }
        return "";
    }
    
    /**
     * 根据IP获取IP信息详情
     * 
     * @param ip
     * @return
     */
    public static IpInfo lookupIp(String ip) {
        try {
            String str = HttpUtil.sendGet("http://ip.taobao.com/service/getIpInfo.php", "ip=" + ip);
            JSONObject jsonobject = JSONObject.fromObject(str);
            ResObj j = (ResObj) JSONObject.toBean(jsonobject, ResObj.class);
            if (j != null) {
                return j.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static class ResObj {
        
        private String code;
        
        private IpInfo data;
        
        /**
         * @return 获取 code属性值
         */
        public String getCode() {
            return code;
        }
        
        /**
         * @param code 设置 code 属性值为参数值 code
         */
        public void setCode(String code) {
            this.code = code;
        }
        
        /**
         * @return 获取 data属性值
         */
        public IpInfo getData() {
            return data;
        }
        
        /**
         * @param data 设置 data 属性值为参数值 data
         */
        public void setData(IpInfo data) {
            this.data = data;
        }
        
    }
    
    public static class IpInfo implements Serializable {
        
        private String country;
        
        private String country_id;
        
        private String area;
        
        private String area_id;
        
        private String region;
        
        private String region_id;
        
        private String city;
        
        private String city_id;
        
        private String county;
        
        private String county_id;
        
        private String isp;
        
        private String isp_id;
        
        private String ip;
        
        /**
         * @return 获取 country属性值
         */
        public String getCountry() {
            return country;
        }
        
        /**
         * @param country 设置 country 属性值为参数值 country
         */
        public void setCountry(String country) {
            this.country = country;
        }
        
        /**
         * @return 获取 country_id属性值
         */
        public String getCountry_id() {
            return country_id;
        }
        
        /**
         * @param country_id 设置 country_id 属性值为参数值 country_id
         */
        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }
        
        /**
         * @return 获取 area属性值
         */
        public String getArea() {
            return area;
        }
        
        /**
         * @param area 设置 area 属性值为参数值 area
         */
        public void setArea(String area) {
            this.area = area;
        }
        
        /**
         * @return 获取 area_id属性值
         */
        public String getArea_id() {
            return area_id;
        }
        
        /**
         * @param area_id 设置 area_id 属性值为参数值 area_id
         */
        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }
        
        /**
         * @return 获取 region属性值
         */
        public String getRegion() {
            return region;
        }
        
        /**
         * @param region 设置 region 属性值为参数值 region
         */
        public void setRegion(String region) {
            this.region = region;
        }
        
        /**
         * @return 获取 region_id属性值
         */
        public String getRegion_id() {
            return region_id;
        }
        
        /**
         * @param region_id 设置 region_id 属性值为参数值 region_id
         */
        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }
        
        /**
         * @return 获取 city属性值
         */
        public String getCity() {
            return city;
        }
        
        /**
         * @param city 设置 city 属性值为参数值 city
         */
        public void setCity(String city) {
            this.city = city;
        }
        
        /**
         * @return 获取 city_id属性值
         */
        public String getCity_id() {
            return city_id;
        }
        
        /**
         * @param city_id 设置 city_id 属性值为参数值 city_id
         */
        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
        
        /**
         * @return 获取 county属性值
         */
        public String getCounty() {
            return county;
        }
        
        /**
         * @param county 设置 county 属性值为参数值 county
         */
        public void setCounty(String county) {
            this.county = county;
        }
        
        /**
         * @return 获取 county_id属性值
         */
        public String getCounty_id() {
            return county_id;
        }
        
        /**
         * @param county_id 设置 county_id 属性值为参数值 county_id
         */
        public void setCounty_id(String county_id) {
            this.county_id = county_id;
        }
        
        /**
         * @return 获取 isp属性值
         */
        public String getIsp() {
            return isp;
        }
        
        /**
         * @param isp 设置 isp 属性值为参数值 isp
         */
        public void setIsp(String isp) {
            this.isp = isp;
        }
        
        /**
         * @return 获取 isp_id属性值
         */
        public String getIsp_id() {
            return isp_id;
        }
        
        /**
         * @param isp_id 设置 isp_id 属性值为参数值 isp_id
         */
        public void setIsp_id(String isp_id) {
            this.isp_id = isp_id;
        }
        
        /**
         * @return 获取 ip属性值
         */
        public String getIp() {
            return ip;
        }
        
        /**
         * @param ip 设置 ip 属性值为参数值 ip
         */
        public void setIp(String ip) {
            this.ip = ip;
        }
        
    }
}
