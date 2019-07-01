/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年10月19日
 *******************************************************************************/

package com.volume2.ch1.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 服务端处理通道.这里只是打印一下请求的内容，并不对请求进行任何的响应 DiscardServerHandler 继承自
 * ChannelHandlerAdapter， 这个类实现了ChannelHandler接口， ChannelHandler提供了许多事件处理的接口方法，
 * 然后你可以覆盖这些方法。 现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 这里我们覆盖了chanelRead()事件处理方法。 每当从客户端收到新的数据时， 这个方法会在收到消息时被调用，
	 * 这个例子中，收到的消息的类型是ByteBuf
	 * 
	 * @param ctx
	 *            通道处理的上下文信息
	 * @param msg
	 *            接收的消息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf in = (ByteBuf) msg;
			// 打印客户端输入，传输过来的的字符
			System.out.print(in.toString(CharsetUtil.UTF_8));
		} finally {
			/**
			 * ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。
			 * 请记住处理器的职责是释放所有传递到处理器的引用计数对象。
			 */
			// 抛弃收到的数据
			ReferenceCountUtil.release(msg);
		}
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}
	
	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandler#handlerAdded(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerAdded");
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandler#handlerRemoved(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved");
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandler#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
	}

}
