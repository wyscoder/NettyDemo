package org.example.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.config.SimpleNettyClientHandler;

/**
 * @Author: wys
 * @Description:
 * @Date: 2020/7/2 0:00
 * @FileName: SimpleNettyClient
 */
public class SimpleNettyClient {

    public void connect(String host, int port) throws Exception{
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);

            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new SimpleNettyClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(host,port).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }


    }
    public static void main(String[] args) throws Exception {
        SimpleNettyClient client = new SimpleNettyClient();
        client.connect("127.0.0.1",8088);
    }
}
