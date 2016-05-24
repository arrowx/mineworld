
package com.mineworld.cache;

import io.netty.channel.ChannelId;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public class AcsChannelIdCache extends EhcacheCache<String, ChannelId> {
    
    private static final String CACHE_NAME = "AcsChannelIdCache";
    
    private static AcsChannelIdCache cache = new AcsChannelIdCache();
    
    private AcsChannelIdCache() {
        super();
    }
    
    public static AcsChannelIdCache getInstance() {
        return cache;
    }
    
    @Override
    public String getCacheName() {
        return CACHE_NAME;
    }
    
}
