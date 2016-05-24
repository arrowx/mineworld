package com.mineworld.service;

import java.util.List;

import com.mineworld.entity.SGateUpgrate;

/**
 * @author 作者:lmm
 * @version 创建时间：2016年1月16日 上午10:05:32
 */
public interface IGateUpgrateService {
	/**
	 * 根据升级程序包id查询升级程序包信息
	 * @param id  升级程序包主键
	 * @return
	 */
	SGateUpgrate findUpgratePkgById(String id);
    
	/**
	 * 根据程序包类型获取最新包
	 * @param type 包类型
	 * @param need 是否强制更新(1是 2否 3不限制)
	 * @return
	 */
	SGateUpgrate findLastUpgratePkgByType(String type,String need);
	
	/**
	 * 根据程序包是否强制升级获取最新包
	 * @param need 是否强制更新(1是 2否 3不限制)
	 * @return
	 */
	List<SGateUpgrate> findLastUpgratePkg(String need);
}
