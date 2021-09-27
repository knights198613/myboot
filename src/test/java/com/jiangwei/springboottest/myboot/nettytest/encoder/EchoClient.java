package com.jiangwei.springboottest.myboot.nettytest.encoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author: weijiang
 * @date: 2021/9/27
 * @description: 客户端
 **/
public class EchoClient {

    public void connection(String host, int port) throws Exception {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoClientChannelHandler());
                        }
                    }).group(clientGroup);
            ChannelFuture channelFuture = client.connect(host, port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            clientGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        try {
            echoClient.connection("127.0.0.1", 8099);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class EchoClientChannelHandler extends ChannelInboundHandlerAdapter {
    int counter = 0;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i=0; i<100; i++) {
            String msg = "[Hi, WeiJiang,Welcome Study netty I/O pragrames]"+"$_";
            ByteBuf byteBuf = Unpooled.buffer(msg.getBytes().length);
            byteBuf.writeBytes(msg.getBytes());
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String response = (String) msg;
        System.out.println("The EchoClient has recived:" + response + ", counter = " + ++counter);
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
