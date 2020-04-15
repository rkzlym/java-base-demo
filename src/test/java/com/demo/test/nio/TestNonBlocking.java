package com.demo.test.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.time.LocalDateTime;
import java.util.Iterator;

public class TestNonBlocking {

    @Test
    public void client() throws IOException {
        // 1. 获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        // 2. 切换非阻塞模式
        sChannel.configureBlocking(false);
        // 3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 4. 发送数据给服务端
        buf.put(LocalDateTime.now().toString().getBytes());
        buf.flip();
        sChannel.write(buf);
        buf.clear();
        // 5. 关闭通道
        sChannel.close();
    }

    @Test
    public void server() throws IOException {
        // 1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        // 2. 切换成非阻塞模式
        ssChannel.configureBlocking(false);
        // 3. 绑定连接
        ssChannel.bind(new InetSocketAddress(9898));
        // 4. 获取选择器
        Selector selector = Selector.open();
        // 5. 将通道注册到选择器上, 并且指定"监听接收事件"
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 6. 轮询式地获取选择器上已经"准备就绪"的事件
        while(selector.select() > 0){
            // 7. 获取当前选择器中所有注册的选择键(已就绪的监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                // 8. 获取准备就绪的是事件
                SelectionKey sk = iterator.next();
                // 9. 判断具体什么事件准备就绪
                if(sk.isAcceptable()){
                    // 10. 若接收就绪，就获取客户端的连接
                    SocketChannel sChannel = ssChannel.accept();
                    // 11. 切换非阻塞模式
                    sChannel.configureBlocking(false);
                    // 12. 将该通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);
                } else if(sk.isReadable()){
                    // 13. 获取当前选择器上读就绪状态的通道
                    SocketChannel sChannel = (SocketChannel) sk.channel();
                    // 14. 读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = sChannel.read(buf)) > 0){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                // 15. 取消选择键
                iterator.remove();
            }
        }
    }

}
