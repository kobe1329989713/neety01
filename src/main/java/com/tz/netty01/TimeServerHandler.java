package com.tz.netty01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * <br/> author：Kobe
 * <br/> date：2017/7/5 0005
 * <br/> time：12:00
 * <br/> now：星期三
 * <br/> description：<br/>
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	//@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];// 获得缓冲区可读的字节数
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("the time server receive order : " + body);
		String currentTime = "query time order".equalsIgnoreCase(body) ? new Date(
				System.currentTimeMillis()).toString() : "bad order";
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);// 性能考虑，仅将待发送的消息发送到缓冲数组中，再通过调用flush方法，写入channel中
	}

	//@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();// 将消息发送队列中的消息写入到SocketChannel中发送给对方。
	}

}
