package org.example.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.example.config.SimpleNettyServerHandler;

/**
 * @Author: wys
 * @Description: 简单的netty demo
 * @Date: 2020/7/1 23:32
 * @FileName: SimpleSocketServer
 *
 * 过程：
 * 1、配置绑定端口
 *
 * 2、配置handler
 * 3、运行
 */
public class SimpleNettyServer {

    public void bind(int port) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new SimpleNettyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            System.out.println("server start");

            channelFuture.channel().closeFuture().sync();
        }catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new SimpleNettyServer().bind(port);
    }

}
