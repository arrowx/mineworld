/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.handler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mineworld.cache.AcsChannelIdCache;
import com.mineworld.dao.SessionFactory;
import com.mineworld.dao.map.UserMapper;
import com.mineworld.dao.obj.User;
import com.mineworld.entity.SGateUpgrate;
import com.mineworld.model.BaseMessage;
import com.mineworld.model.GetInfoMessage;
import com.mineworld.model.GetInfoReply;
import com.mineworld.model.HeartBeatMessage;
import com.mineworld.model.HeartBeatReply;
import com.mineworld.model.LoginMessage;
import com.mineworld.model.LoginReply;
import com.mineworld.model.RestartMessage;
import com.mineworld.model.RestartReply;
import com.mineworld.model.ServerMessage;
import com.mineworld.model.UpgradeAcsMessage;
import com.mineworld.model.UpgradeAcsReply;
import com.mineworld.model.UpgradeZllMessage;
import com.mineworld.model.UpgradeZllReply;

//import com.app.mongodb.mongoservice.IMongoDbService;

import com.mineworld.server.AcsTcpServer;
import com.mineworld.service.IAcsService;
import com.mineworld.service.IGateUpgrateService;
import com.mineworld.utils.AcsPropertiesConfig;
import com.mineworld.utils.ByteUtils;
import com.mineworld.utils.Constant;
import com.mineworld.utils.IpLookupUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/*import common.context.SystemContext;*/
/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */

@Component
@Sharable
public class AcsTcpServerHandler extends SimpleChannelInboundHandler<BaseMessage> implements IAcsService {
    
    /*@Autowired
    private IMongoDbService mongo;*/
        
    @Autowired
    private IGateUpgrateService gatewayService;
    
    private static final Logger logger = LoggerFactory.getLogger(AcsTcpServerHandler.class);
    
    private AcsTcpServer server;
    
    private SGateUpgrate zllSgu;
    
    private UpgradeZllMessage uzm;
    
    private SGateUpgrate acsSgu;
    
    private UpgradeAcsMessage uam;
    
    private long query_time = System.currentTimeMillis();
    
    private final long split_time = 10 * 60 * 1000;
    
    /*private static final boolean sutoupgrade = SystemContext.getInstance()
        .getBoolean("terminal.acs.autoupgrade", false);*/
    private static final boolean sutoupgrade = false;
    
    /**
     * 
     */
    public AcsTcpServerHandler() {
        super();
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        try {
            if (logger.isWarnEnabled()) {
                logger.warn("IP地址:" + getIpAddress(ctx.channel()) + "连接ACS服务器");
            }
            server.addChannel(ctx.channel());
            reqLogin(ctx.channel());
            super.channelActive(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        removeChannel(ctx.channel());
    }
    
    private String getIpAddress(Channel channel) {
        SocketAddress addr = channel.remoteAddress();
        if (addr != null) {
            InetSocketAddress insocket = (InetSocketAddress) addr;
            return insocket.getAddress().getHostAddress();
        }
        return null;
    }
    
    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, BaseMessage msg) throws Exception {
        if (msg == null) {
            return;
        }
        logger.info(msg.toString());
        if (msg instanceof BaseMessage) {
            controlMessage(ctx.channel(), msg);
        } else {
            logger.info("client said (Object):" + msg);
        }
    }
    
    /**
     * 处理消息
     * 
     * @param channel
     * @param msg
     */
    private void controlMessage(Channel channel, BaseMessage msg) {
        if (!checkLogin(channel) && needLogin(msg)) {
            reqLogin(channel);
            return;
        }
        if (sutoupgrade) {
            checkVersion(channel);
        }
        
        if (msg instanceof LoginReply) {
            controlLoginReplay(channel, (LoginReply) msg);
        } else if (msg instanceof HeartBeatMessage) {
            // 心跳包
            channel.writeAndFlush(HeartBeatReply.instance);
        } else if (msg instanceof RestartReply) {
            if (logger.isDebugEnabled()) {
                logger.debug("restart gateway reply...");
            }
            removeChannel(channel);
        } else if (msg instanceof UpgradeZllReply) {
            controlUpgradeZllReplay(channel, (UpgradeZllReply) msg);
        } else if (msg instanceof UpgradeAcsReply) {
            controlUpgradeAcsReplay(channel, (UpgradeAcsReply) msg);
        } else if (msg instanceof GetInfoReply) {
            controlGateInfoReplay(channel, (GetInfoReply) msg);
        } else {
            logger.info("等待实现");
        }
    }
    
    /**
     * @param msg
     */
    private void controlGateInfoReplay(Channel channel, GetInfoReply msg) {
        String acct = getAcct(channel);
        if (StringUtils.isBlank(acct)) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("GetInfoReply ..." + msg.toString());
        }
        String zv = tranVersion(msg.getZllVersion());
        String av = tranVersion(msg.getAcsVersion());
        /*Update newdate = new Update();
        newdate.set("zv", zv);
        newdate.set("av", av);
        newdate.set("snid", msg.getSnid());
        newdate.set("_d", DateUtils.formatDateTime(new Date()));
        mongo.updateByField(newdate, "u", acct, MongoTerminal.COLLECTION_NAME);*/
        
        channel.attr(Constant.KEY_ZLL_VER).set(zv);
        channel.attr(Constant.KEY_ACS_VER).set(av);
        
    }
    
    private String tranVersion(byte[] v) {
        return new StringBuilder(5).append(v[0]).append(".").append(v[1]).append(".").append(v[2]).toString();
    }
    
    /**
     * @param msg
     */
    private void controlUpgradeAcsReplay(Channel channel, UpgradeAcsReply msg) {
        String acct = getAcct(channel);
        if (StringUtils.isBlank(acct)) {
            return;
        }
        //mongo.updateByField("r", true, "u", acct, MongoUpgradeAcs.COLLECTION_NAME);
        removeChannel(channel);
    }
    
    /**
     * @param msg
     */
    private void controlUpgradeZllReplay(Channel channel, UpgradeZllReply msg) {
        String acct = getAcct(channel);
        if (StringUtils.isBlank(acct)) {
            return;
        }
        switch (msg.getResult()) {
            case 0x01:
                //mongo.updateByField("r", "OK", "u", acct, MongoUpgradeZll.COLLECTION_NAME);
                break;
            case 0x02:
                //mongo.updateByField("r", "File Too big", "u", acct, MongoUpgradeZll.COLLECTION_NAME);
                break;
            case 0x03:
                //mongo.updateByField("r", "md5 error", "u", acct, MongoUpgradeZll.COLLECTION_NAME);
                break;
            default:
                //mongo.updateByField("r", "OK", "u", acct, MongoUpgradeZll.COLLECTION_NAME);
                break;
        }
    }
    
    /**
     * 处理登录消息
     * 
     * @param channel
     * @param msg
     */
    private void controlLoginReplay(Channel channel, LoginReply msg) {
        // 客户端登录包
        if (logger.isInfoEnabled()) {
            logger.info("get use name and password:" + msg.getAcct() + " " + msg.getPass());
        }
        if (checkEquipmentPassword(channel,msg)) {
	        	//检查是否登录
	        	if(checkIsLogin(channel,msg))
	        	{
	        		channel.writeAndFlush(ServerMessage.USER_LOGINED);
	                removeChannel(channel);
	        	}else{
		            // 设备登录验证成功
		            String eid = msg.getAcct();
		            channel.attr(Constant.KEY_TER_ACCOUNT).set(eid);
		            channel.writeAndFlush(ServerMessage.LOGIN_OK);
		            
		            channel.writeAndFlush(GetInfoMessage.instance);
		            
		            AcsChannelIdCache c = AcsChannelIdCache.getInstance();
		            c.put(eid, channel.id());
		           /* Update newdate = new Update();
		            String ip = getIpAddress(channel);
		            newdate.set("ol", true);
		            newdate.set("_d", DateUtils.formatDateTime(new Date()));
		            newdate.set("ip", ip);
		            newdate.set("remark", getRemark(eid, channel, ip));
		            //mongo.updateByField(newdate, "u", eid, MongoTerminal.COLLECTION_NAME);
		            */
		        	}
            } else {
            // 登录失败
            channel.writeAndFlush(ServerMessage.LOGIN_FAIL);
            removeChannel(channel);
        }
    }
    
    /**
     * @param eid
     * @param channel
     * @return
     */
    private String getRemark(String eid, Channel channel, String ip) {
        String ret = AcsPropertiesConfig.getProperty(eid);
        if (StringUtils.isNotBlank(ret)) {
            return ret;
        }
        IpLookupUtil.IpInfo ipInfo = IpLookupUtil.lookupIp(ip);
        if (ipInfo != null) {
            return ipInfo.getCity();
        }
        return "";
    }
    
    private boolean checkIsLogin(Channel channel,LoginReply msg)
    {
    	String acct = msg.getAcct();
        if (StringUtils.isNotBlank(acct)) {
            ChannelId cid = AcsChannelIdCache.getInstance().get(acct);
            if(cid != null)
            {
            	logger.debug("exist acct 存在 : " + acct);
            	return true;
            }
            else{
            	logger.debug("exist acct 不存在: " + acct);
            }
        }
        return false;
    }
    
    /**
     * 登录认证（包括两种认证方式：出厂初始账号密码和会员账号密码）
     * 
     * @param str
     * @return
     */
    private boolean checkEquipmentPassword(Channel channel,LoginReply msg) {
        // GatewayDTO dto = new GatewayDTO();
        // dto.setAccount(msg.getAcct());
        // dto.setPassword(msg.getPass());
        // return gatewayService.validateGateUser(dto);
    	/*String acct = getAcct(channel);*/
    	//import com.mineworld.dao.SessionFactory;
    	SqlSessionFactory factory = SessionFactory.getInstance();
    	System.out.println("check database");
		SqlSession session = null;
		try {
			session = factory.openSession();
			UserMapper userMapper = session.getMapper(UserMapper.class);
			List<User> result2 = userMapper.select(msg.getAcct());
			if (result2.size() == 0 || !result2.get(0).getPassword().equals(msg.getPass())) {
				System.out.println("get login 用户名错误！");
				return false;
			}else{

				System.out.println("username ：" + result2.get(0).getUsername());
				System.out.println("password ：" + result2.get(0).getPassword());
				System.out.println("get login 登录成功！");
				return true;
			}		
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		} finally {
			if (session != null) {
				session.close();
			}
		}
        return true;
    }
    
    /**
     * 检查是否已经登录认证，
     * 
     * @param channel
     * @return true表示已经登录。否则表示未登录
     */
    private boolean checkLogin(Channel channel) {
        String userid = getAcct(channel);
        if (StringUtils.isNotBlank(userid))
        {
        	System.out.println("exist userid : " + userid);;
            return true;
        }
        return false;
    }
    
    /**
     * 检查版本，
     * 
     * @param channel
     */
    private void checkVersion(Channel channel) {
        if (queryNewUpgrades()) { // 没有新版本
            return;
        }
        
        String zv = channel.attr(Constant.KEY_ZLL_VER).get();
        if (this.zllSgu != null && zv != null && uzm != null && (!StringUtils.equals(zv, this.zllSgu.getVersioncode()))) {
            channel.writeAndFlush(uzm);
        }
        
        String av = channel.attr(Constant.KEY_ACS_VER).get();
        if (this.acsSgu != null && av != null && uam != null && (!StringUtils.equals(av, this.acsSgu.getVersioncode()))) {
            channel.writeAndFlush(uam);
        }
        
    }
    
    private String getAcct(Channel channel) {
        return channel.attr(Constant.KEY_TER_ACCOUNT).get();
    }
    
    /**
     * 发送需要登录的消息
     * 
     * @param channel
     */
    private void reqLogin(Channel channel) {
        channel.writeAndFlush(LoginMessage.instance);
    }
    
    private void removeChannel(Channel channel) {
        server.removeChannel(channel);
        String acct = getAcct(channel);
        if (StringUtils.isNotBlank(acct)) {
            ChannelId cid = AcsChannelIdCache.getInstance().get(acct);
            if (cid != null && cid.equals(channel.id())) {
                ///mongo.updateByField("ol", false, "u", acct, MongoTerminal.COLLECTION_NAME);
                if (logger.isWarnEnabled()) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("ACS连接断开。。。。ip:").append(getIpAddress(channel)).append(",acct:").append(getAcct(channel));
                    logger.warn(sb.toString());
                }
                AcsChannelIdCache.getInstance().remove(acct);
            }
        }
        channel.close();
    }
    
    /**
     * 检测消息类型是否需要登录
     * 
     * @param bm
     * @return
     */
    private boolean needLogin(BaseMessage bm) {
        if (bm instanceof LoginReply)
            return false;
        return true;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        try {
            // ChannelUtil.removeChannel(ctx.channel(), util);
            removeChannel(ctx.channel());
        } catch (Exception e) {
            logger.error("Unexpected exception from downstream.", cause);
        } finally {
            ctx.channel().close();
            ctx.close();
        }
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        super.channelReadComplete(ctx);
    }
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                /* 读超时 */
                if (logger.isWarnEnabled()) {
                    logger.warn("===服务端===(READER_IDLE 读超时)");
                }
                // ChannelUtil.removeChannel(ctx.channel(), util);
                removeChannel(ctx.channel());
            } else if (event.state() == IdleState.WRITER_IDLE) {
                /* 写超时 */
                logger.error("===服务端===(WRITER_IDLE 写超时)");
            } else if (event.state() == IdleState.ALL_IDLE) {
                /* 总超时 */
                logger.error("===服务端===(ALL_IDLE 总超时)");
            }
        }
    }
    
    /**
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        removeAllGateway();
        super.finalize();
    }
    
    /**
     * 删除所有的网关
     */
    private void removeAllGateway() {
        //mongo.removeAll(MongoTerminal.COLLECTION_NAME);
    }
    
    /**
     * @param server 设置 server 属性值为参数值 server
     */
    public void setServer(AcsTcpServer server) {
        this.server = server;
        //queryNewUpgrades();
        //removeAllGateway();
    }
    
    /**
     * 
     * @see com.app.terminal.acs.service.IAcsService#restartGateways(java.lang.String)
     */
    @Override
    public boolean restartGateways(String accts) {
        /* 记录数据库，并记录记录状态，等待网关回复*/
        return sendGatewayMessage(accts, RestartMessage.instance, null);
    }
    
    /**
     * @param accts
     * @param msg
     * @param call
     * @return
     */
    public boolean sendGatewayMessage(String accts, BaseMessage msg, Callback call) {
        if (StringUtils.isBlank(accts)) {
            return true;
        }
        AcsChannelIdCache c = AcsChannelIdCache.getInstance();
        String[] arrAccts = accts.split(";");
        for (String acct : arrAccts) {
            try {
                ChannelId cid = c.get(acct);
                if (cid == null) {
                    continue;
                }
                Channel channel = server.getServer().find(cid);
                if (channel != null) {
                    channel.writeAndFlush(msg);
                    if (call != null) {
                        call.Callback(acct);
                    }
                }
            } catch (Exception e) {
                logger.error("发送消息失败", e);
            }
        }
        return true;
    }
    
    private boolean queryNewUpgrades() {
        
        if (zllSgu == null || acsSgu == null || (System.currentTimeMillis() - query_time) > split_time) {
            
            query_time = System.currentTimeMillis();
            
            zllSgu = gatewayService.findLastUpgratePkgByType("zll", "1");
            
            if (zllSgu != null) {
                
                short size = 0;
                try {
                    size = Short.valueOf(zllSgu.getAppsize().replace("KB", ""));
                } catch (Exception e) {
                    zllSgu = null;
                }
                
                String[] vs = zllSgu.getVersioncode().split("\\.");
                byte[] version = new byte[3];
                try {
                    for (int i = 0; i < 3; i++) {
                        version[i] = ByteUtils.intToBytes(Integer.valueOf(vs[i]), 1)[0];
                    }
                } catch (Exception e) {
                    zllSgu = null;
                }
                this.uzm = new UpgradeZllMessage(version, size, zllSgu.getMd5str(), zllSgu.getResourceurl());
            } else {
                this.uzm = null;
            }
            acsSgu = gatewayService.findLastUpgratePkgByType("acs", "1");
            
            if (acsSgu != null) {
                short size = 0;
                try {
                    size = Short.valueOf(acsSgu.getAppsize().replace("KB", ""));
                } catch (Exception e) {
                    acsSgu = null;
                }
                
                String[] vs = acsSgu.getVersioncode().split("\\.");
                byte[] version = new byte[3];
                try {
                    for (int i = 0; i < 3; i++) {
                        version[i] = ByteUtils.intToBytes(Integer.valueOf(vs[i]), 1)[0];
                    }
                } catch (Exception e) {
                    acsSgu = null;
                }
                
                this.uam = new UpgradeAcsMessage(version, size, acsSgu.getMd5str(), acsSgu.getResourceurl());
            } else {
                this.uam = null;
            }
        }
        
        return zllSgu == null && acsSgu == null;
        
    }
    
    /**
     * 
     * @see com.app.terminal.acs.service.IAcsService#upgradeZlls(java.lang.String, int, int, java.lang.String)
     */
    @Override
    public boolean upgradeZlls(String accts, final byte[] version, short size, String md5, String uri) {
        UpgradeZllMessage uzm = new UpgradeZllMessage(version, size, md5, uri);
        
        return sendGatewayMessage(accts, uzm, new Callback() {
            
            public void Callback(String a) {
                /*MongoUpgradeZll muz = new MongoUpgradeZll(a, version);
                mongo.insert(muz);*/
            }
        });
    }
    
    /**
     * 
     * @see com.app.terminal.acs.service.IAcsService#upgradeZlls(java.lang.String, int, int, java.lang.String)
     */
    @Override
    public boolean upgradeAcss(String accts, final byte[] version, short size, String md5, String uri) {
        UpgradeAcsMessage uzm = new UpgradeAcsMessage(version, size, md5, uri);
        
        return sendGatewayMessage(accts, uzm, new Callback() {
            
            public void Callback(String a) {
                /*MongoUpgradeAcs mua = new MongoUpgradeAcs(a, version);
                mongo.insert(mua);
*/            }
        });
    }
    
    interface Callback {
        void Callback(String a);
    }
}
