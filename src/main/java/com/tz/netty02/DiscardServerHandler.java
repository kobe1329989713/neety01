package com.tz.netty02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * <br/> author：Kobe
 * <br/> date：2017/7/3 0003
 * <br/> time：17:05
 * <br/> now：星期一
 * <br/> description：<br/>
 * XxxHandler
 *
 *  channelHandlerAdapter 实现的 ChannelHandler interface ,这个 interface 是事件处理的接口。
 *  这个接口，是别人实现好了，我只需继承过来使用的。
 *  它的几个方法主要如下作用：
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {
	// 收到消息时被调用。
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		// 它是读消息，然后就是释放掉。
		((ByteBuf) msg).release(); // (3)
	}
	// exception 被 perform
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	// 这个写完，写一个 main 方法，
	// 启动它。

}
