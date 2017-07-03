package com.tz.netty01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <br/> author：Kobe
 * <br/> date：2017/7/5 0005
 * <br/> time：12:22
 * <br/> now：星期三
 * <br/> description：<br/>
 */
public class TimeServer {

	public void bind(int port) {
		// 配置NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();// 连接线程
		EventLoopGroup workerGroup = new NioEventLoopGroup();// 处理线程组

		try {
			// ServerBootstrap 是一个启动NIO服务的辅助启动类
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
			bootstrap.childHandler(new ChildChannelHandler());
			// 绑定端口，同步等待成功
			ChannelFuture future = bootstrap.bind(port).sync();
			// 等待服务端监听端口关闭，等待服务端链路关闭之后main函数才退出
			future.channel().closeFuture().sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}

//
class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new TimeServerHandler());

	}


	public static void main(String[] args) {
		int port = 8080;
		if (args != null && args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		new TimeServer().bind(port);
	}
}
