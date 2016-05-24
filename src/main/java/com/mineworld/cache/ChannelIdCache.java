
package com.mineworld.cache;

import io.netty.channel.ChannelId;

import java.util.List;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public class ChannelIdCache extends EhcacheCache<String, List<ChannelId>> {
    
    private static final String CACHE_NAME = "ChannelIdCache";
    
    private static ChannelIdCache cache = new ChannelIdCache();
    
    private ChannelIdCache() {
        super();
    }
    
    public static ChannelIdCache getInstance() {
        return cache;
    }
    
    @Override
    public String getCacheName() {
        return CACHE_NAME;
    }
    
    public void remove(String key, ChannelId cid) {
        List<ChannelId> cids = get(key);
        if (cids == null || cids.size() == 0) {
            return;
        }
        cids.remove(cid);
    }
}
