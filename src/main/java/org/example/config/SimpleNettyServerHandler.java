package org.example.config;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: wys
 * @Description:
 * @Date: 2020/7/1 23:55
 * @FileName: SimpleNettyServerHandler
 */
public class SimpleNettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("SimpleNettyServerHandler.channelRead");

        ByteBuf result = (ByteBuf) msg;
        byte[] bytesMsg = new byte[result.readableBytes()];

        result.readBytes(bytesMsg);
        String resultStr = new String(bytesMsg);

        System.out.println("Client said:" + resultStr);

        result.release();

        String response = "hello client";

        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
