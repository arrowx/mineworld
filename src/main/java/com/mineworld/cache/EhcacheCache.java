/**
 * 
 */
package com.mineworld.cache;

import java.io.IOException;
import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @company 荣事达
 * @author xuqingqing
 * @date   2015-09-22
 * Ehcache 缓存抽象处理类
 */
public abstract class EhcacheCache<K, V> {

	private static CacheManager cacheManager;

	EhcacheCache() {
		initCache();
	}

	private synchronized static void initCache() {
		if (cacheManager != null) {
			return;
		}
		String location = "classpath:cache/ehcache.xml";
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resource = resolver.getResource(location);
		URL url = null;
		try {
			url = resource.getURL();
			cacheManager = new CacheManager(url);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"not found cache config file!!! url=" + location);
		}
	}

	public void put(K key, V value) {
		Element e = new Element(key, value);
		getCache().put(e);
	}

	public Cache getCache() {
		return cacheManager.getCache(getCacheName());
	}

	public V get(K key) {
		Element element = getCache().get(key);
		return (V) (element == null ? null : element.getObjectValue());
	}

	public V remove(K key) {
		Element element = getCache().removeAndReturnElement(key);
		if (element == null) {
			return null;
		}
		return (V) element.getObjectValue();
	}

	/**
	 * @return the cacheManager
	 */
	public static CacheManager getCacheManager() {
		return cacheManager;
	}

	public boolean containsKey(K key) {
		return getCache().isKeyInCache(key);
	}

	public abstract String getCacheName();

}
