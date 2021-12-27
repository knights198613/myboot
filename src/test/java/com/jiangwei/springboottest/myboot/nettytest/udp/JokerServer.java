package com.jiangwei.springboottest.myboot.nettytest.udp;

import com.baomidou.mybatisplus.extension.api.R;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @author: weijiang
 * @date: 2021/12/27
 * @description: 一个用udp协议实现的消化服务端
 **/
public class JokerServer {

    public static void main(String[] args) {
        JokerServer jokerServer = new JokerServer();
        jokerServer.doStart();

    }

    private void doStart() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioDatagramChannel.class)
                    //接手广播
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new JokerHandler());
            ChannelFuture channelFuture = bootstrap.bind(8081).sync();
            System.out.println("########The Joker Server Starting.########");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
        }
    }


    class JokerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        private final String client_req = "make a jokes";
        private final String[] jokes_dic = {
                "一个男生暗恋一个女生很久了。一天自习课上，男生偷偷的传了小纸条给女生，上面写着“其实我注意你很久了”。不一会儿，女生传了另一张纸条，男生心急火燎的打开一看“拜托你不要告诉老师，我保证以后再也不嗑瓜子了”。。。。。。男生一脸懵逼",
                "昨天因为一件事骂儿子，说你妈妈是猪，你也是头猪。儿子却反过来说我：爸爸你怎么这么衰，娶了一头猪，还生了一只猪！你说你这熊孩子，这是不是找打。",
                "火云邪神苦练多年，终于将蛤蟆功练至顶级并成功产下8个小蝌蚪。",
                "老婆永远是对的，这话永远也是对的。但老婆没想到的是，她不一定永远是老婆",
                "人生天地间没有谁是容易的，就算是思聪也得每天犯愁怎么花钱。",
                "今天去理发，洗剪吹68，烫发和染发668。我就做了个洗剪吹，结账的时候发现居然收我66"
        };
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String req = msg.content().toString(CharsetUtil.UTF_8);
            System.out.println("接受到的请求为:"+ req);
            if(client_req.equals(req)) {
                Random random = new Random();
                String rspJokeContent = "笑话来了：" + jokes_dic[random.nextInt(jokes_dic.length-1)];
                ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(rspJokeContent, CharsetUtil.UTF_8), msg.sender()));
            }
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
            cause.printStackTrace();
        }
    }
}
