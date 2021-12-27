package com.jiangwei.springboottest.myboot.nettytest.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author: weijiang
 * @date: 2021/12/27
 * @description: 一个 upd 协议的笑话获取客户端
 **/
public class JokerClient {

    public static void main(String[] args) {
        String serverIp = "127.0.0.1";
        int serverPort = 8081;
        JokerClient jokerClient = new JokerClient();
        jokerClient.doStart(serverPort, serverIp);
    }

    private void doStart(int serverPort, String serverIp) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioDatagramChannel.class)
                    //接手广播
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new JokerClientHandler());
            Channel ch = bootstrap.bind(0).sync().channel();
            System.out.println("########The Joker Client Starting.########");
            final String req = "make a jokes";
            while (true) {
                ChannelFuture channelFuture = ch.writeAndFlush(
                        new DatagramPacket(Unpooled.copiedBuffer(req, CharsetUtil.UTF_8),
                                new InetSocketAddress(serverIp,serverPort))
                ).sync();
                /*if(!ch.closeFuture().await(15000)) {
                    System.out.println("获取笑话失败！");
                }*/
                TimeUnit.SECONDS.sleep(5);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
        }
    }

    class JokerClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String rsp = msg.content().toString(CharsetUtil.UTF_8);
            System.out.println(rsp);
            //ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
            cause.printStackTrace();
        }
    }

}
