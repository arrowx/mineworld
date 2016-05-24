/**
 * 
 */
package com.mineworld.entity;

import java.io.Serializable;


/**
 * 网关升级程序Entity
 * @author lmm
 * @version 2016-01-13
 */
public class SGateUpgrate implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private String appid;		// 类别
	private String ostype;		// 类型
	private String versioncode;		// 版本号
	private String versionname;		// 版本名
	private String resourceurl;		// 资源地址
	private String remark;		// 备注
	private String necessary;		// 是否必须
	private String appsize;		// 文件大小
	private String storagepath;		// 存储路径
	private String originalname;		// 原始名称
	private String storagename;		// 存储名称
	private String md5str;		// md5校验码
	private String categoryname;//版本类型
	
	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public SGateUpgrate() {
		super();
	}


	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}
	
	public String getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(String versioncode) {
		this.versioncode = versioncode;
	}
	
	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}
	
	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getNecessary() {
		return necessary;
	}

	public void setNecessary(String necessary) {
		this.necessary = necessary;
	}
	
	public String getAppsize() {
		return appsize;
	}

	public void setAppsize(String appsize) {
		this.appsize = appsize;
	}
	
	public String getStoragepath() {
		return storagepath;
	}

	public void setStoragepath(String storagepath) {
		this.storagepath = storagepath;
	}
	
	public String getOriginalname() {
		return originalname;
	}

	public void setOriginalname(String originalname) {
		this.originalname = originalname;
	}
	
	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
	
	public String getMd5str() {
		return md5str;
	}

	public void setMd5str(String md5str) {
		this.md5str = md5str;
	}
	
}