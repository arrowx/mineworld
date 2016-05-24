/******************************************************************************
 * Copyright (C) HeFei Royalstar Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合肥荣事达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.mineworld.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mineworld.event.spring.EventUtil;
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
import com.mineworld.utils.ChannelUtil;
import com.mineworld.utils.Constant;
import com.mineworld.utils.ShortUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月14日 xuqingqing
 */
public class MessageDecoder extends ByteToMessageDecoder {

	protected static Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

	@Autowired
	private EventUtil util;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// in.retain();
		if (in.readableBytes() < 3) {
			return;
		}

		byte[] lb = new byte[2];
		in.markReaderIndex();

		in.readBytes(lb);
		short allLength = ShortUtils.bytesToShort(lb, 0);
		// 最大数据量: 512byte
		if (allLength > 512 || allLength < 3) {
			ChannelUtil.removeChannel(ctx.channel(), util);
			return;
		}

		if (in.readableBytes() < (allLength - 2)) {
			in.resetReaderIndex();
			return;
		}

		byte type = in.readByte();
		byte[] byteMsg = null;
		if (allLength > 3) {
			in.resetReaderIndex();
			byteMsg = new byte[allLength];
			in.readBytes(byteMsg);
		}

		BaseMessage bm = createMessage(type, byteMsg);
		if (bm != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("receive:" + ByteBufUtil.hexDump(bm.getMessageByByte()));
			}
			out.add(bm);
		}
		// ReferenceCountUtil.release(in);
	}

	private BaseMessage createMessage(short type, byte[] byteMsg) {
		switch (type) {
		case Constant.MSG_TYPE_RESTART:
			return RestartMessage.instance;
		case Constant.MSG_TYPE_RESTART_REPLY:
			return RestartReply.instance;
		case Constant.MSG_TYPE_GET_INFO:
			return GetInfoMessage.instance;
		case Constant.MSG_TYPE_GET_INFO_REPLY:
			return new GetInfoReply(byteMsg);
		case Constant.MSG_TYPE_UPGRADE_ZLL:
			return new UpgradeZllMessage(byteMsg);
		case Constant.MSG_TYPE_UPGRADE_ZLL_REPLY:
			return UpgradeZllReply.getInstance(byteMsg[3]);
		case Constant.MSG_TYPE_UPGRADE_ACS:
			return new UpgradeAcsMessage(byteMsg);
		case Constant.MSG_TYPE_UPGRADE_ACS_REPLY:
			return UpgradeAcsReply.getInstance(byteMsg[3]);
		case Constant.MSG_TYPE_SERVER:
			return new ServerMessage(byteMsg);
		case Constant.MSG_TYPE_HEART_BEAT:
			return HeartBeatMessage.instance;
		case Constant.MSG_TYPE_HEART_BEAT_REPLY:
			return HeartBeatReply.instance;
		case Constant.MSG_TYPE_LOGIN:
			return LoginMessage.instance;
		case Constant.MSG_TYPE_LOGIN_REPLY:
			return new LoginReply(byteMsg);
		default:
			return null;
		}
	}
}
