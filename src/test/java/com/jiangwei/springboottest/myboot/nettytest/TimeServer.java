package com.jiangwei.springboottest.myboot.nettytest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: weijiang
 * @date: 2021/9/27
 * @description: netty时间服务器Server
 **/
public class TimeServer {

    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //辅助启动服务端
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    //设置处理服务端SocketChannel
                    .channel(NioServerSocketChannel.class)
                    //协议配置
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //Channel处理器
                    .childHandler(new ChildChannelHandler());
            ChannelFuture channelFuture = b.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        int port = 8099;
        TimeServer timeServer = new TimeServer();
        try {
            timeServer.bind(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

/**
 * 用于处理网络I/O的处理类
 */
class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //加入正在的业务处理器
        ch.pipeline().addLast(new TimeServerHandler());
    }
}


class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String reqMessage = new String(req, "UTF-8");
        System.out.println("The Server had recived order:" + reqMessage);

        String currentTime = "QUERY CURRENT TIME".equals(reqMessage.toUpperCase()) ?
                new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()) : "BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}


