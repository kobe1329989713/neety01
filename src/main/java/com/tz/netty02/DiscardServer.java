package com.tz.netty02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <br/> author：Kobe
 * <br/> date：2017/7/3 0003
 * <br/> time：17:24
 * <br/> now：星期一
 * <br/> description：<br/>
 * 启动，DiscardServerHandler 处理类。
 */
public class DiscardServer {
	private int port;

	private DiscardServer(int port) {
		this.port = port;
	}


	private void run() throws Exception {
		// 线程级，处理I/O操作的多线程事件循环器
		// boss，接收进来连接。
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// worker，用它来处理已经被接收的连接。
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		/**
		 * 用来处理已经被接收的连接，
		 * 一旦‘boss’接收到连接，
		 * 就会把连接信息注册到‘worker’上。
		 * 如何知道多少个线程已经被使用，
		 * 如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
		 * 并且可以通过构造函数来配置他们的关系。
		 *
		 *  它与通道channel关系？？？
		 */

		 try {
			ServerBootstrap b = new ServerBootstrap();
			 /**
			  * NioServerSocketChannel 注：sochet 这个东东，
			  * ChannelInitializer，一个特殊处理类。
			  * 目的：配置一个新的Channel。
 			  */
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer(){

				// 配置一个新的Channel。然后这里，可以是一个体系了。
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new DiscardServerHandler()); // 就去到，你自己的handller里面？？？？
				}

				// channel设置。
			}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
			 /**
			  * ？option()是提供给NioServerSocketChannel用来接收进来的连接。
			  * childOption()是提供给由父管道ServerChannel接收到的连接，
			  * 在这个例子中也是NioServerSocketChannel。
			  */



			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync(); // (7)
			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server.
			f.channel().closeFuture().sync();

		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}


	}


	public static void main(String[] args) throws Exception {
		int port;
		if (args.length < 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new DiscardServer(port).run();
	}

	// 只是 server端的启动和它的 handler 类。

}


