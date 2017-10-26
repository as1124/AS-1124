/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年10月19日
 *******************************************************************************/

package com.volume2.ch1.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * HUANG 此处填写 class 信息
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */

public class NettyServer {

	private int port = 0;

	public NettyServer(int linstenPort) {
		this.port = linstenPort;
	}

	public void run() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap serverBoot = new ServerBootstrap();

		serverBoot.group(bossGroup, workerGroup);

		serverBoot = serverBoot.channel(NioServerSocketChannel.class);

		serverBoot.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new DiscardServerHandler());
			}
		});

		serverBoot.option(ChannelOption.SO_BACKLOG, 128);

		serverBoot.childOption(ChannelOption.SO_KEEPALIVE, true);
		try {
			ChannelFuture future = serverBoot.bind(port).sync();

			future.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		try {
			new NettyServer(8088).run();
			System.out.println("结束");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
