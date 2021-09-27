package com.jiangwei.springboottest.myboot.nettytest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
        }finally {
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
        ch.pipeline().addLast(new TimeClientHandler());
    }
}

class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String sendMessage = "QUERY CURRENT TIME";
        ByteBuf byteBuf = Unpooled.buffer(sendMessage.getBytes().length);
        byteBuf.writeBytes(sendMessage.getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] recivedMsg = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(recivedMsg);
        String reMssage = new String(recivedMsg, "UTF-8");
        System.out.println("Recived server Time:"+reMssage);
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
