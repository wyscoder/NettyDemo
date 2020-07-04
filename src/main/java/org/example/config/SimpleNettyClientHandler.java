package org.example.config;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: wys
 * @Description:
 * @Date: 2020/7/2 0:08
 * @FileName: SimpleNettyClientHandler
 */
public class SimpleNettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("SimpleClientHandler.channelRead");

        ByteBuf result = (ByteBuf)msg;

        byte[] results = new byte[result.readableBytes()];
        result.readBytes(results);
        System.out.println("Server said:" + new String(results));

        result.release();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();;
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "你好!";
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }
}
