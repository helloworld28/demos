package com.jim.java8.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Arrays;


public class Client {
    private EventLoopGroup loop = new NioEventLoopGroup();

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Integer[]{1,2,3,4}));
        new Client().run();
    }

    public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
        if (bootstrap != null) {
            final MyInboundHandler handler = new MyInboundHandler(this);
            bootstrap.group(eventLoop);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(handler);
                }
            });
            bootstrap.remoteAddress("192.168.193.90", 11000);
            ChannelFuture connect = bootstrap.connect();
            Channel channel = connect.channel();
            connect.addListener(new ConnectionListener(this));

        }
        return bootstrap;
    }

    public void run() {
        createBootstrap(new Bootstrap(), loop);
    }
}