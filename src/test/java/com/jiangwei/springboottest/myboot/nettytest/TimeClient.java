package com.jiangwei.springboottest.myboot.nettytest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: weijiang
 * @date: 2021/9/27
 * @description:
 **/
public class TimeClient {

    public void connect(int port, String host) throws Exception {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(clientGroup)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChildClientChannelHandler());
            ChannelFuture channelFuture = b.connect(host, port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            clientGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        TimeClient timeClient = new TimeClient();
        try {
            timeClient.connect(8099, "127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * SocketChannel处理器
 */
class ChildClientChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //加入编解码器处理 tcp粘包和读半包
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
        ch.pipeline().addLast(new StringDecoder());
        ch.pipeline().addLast(new TimeClientHandler());
    }
}

class TimeClientHandler extends ChannelInboundHandlerAdapter {
    AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            String sendMessage = "QUERY CURRENT TIME" + System.lineSeparator();
            ByteBuf byteBuf = Unpooled.buffer(sendMessage.getBytes().length);
            byteBuf.writeBytes(sendMessage.getBytes());
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf byteBuf = (ByteBuf) msg;
        byte[] recivedMsg = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(recivedMsg);
        String reMssage = new String(recivedMsg, "UTF-8");*/
        String reMssage = (String) msg;
        System.out.println("Recived server Time:" + reMssage+",counter:"+counter.incrementAndGet());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
