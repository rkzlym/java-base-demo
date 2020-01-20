package com.demo.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class TestPipe {

    @Test
    public void test1() throws IOException {
        // 1. 获取管道
        Pipe pipe = Pipe.open();

        // 2. 将缓冲区的数据写入管道
        Pipe.SinkChannel sinkChannel = pipe.sink();

        // 3. 新建一个缓冲区存放数据
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put("通过单向管道发送数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);

        // 4. 读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buf.flip();
        int len = sourceChannel.read(buf);
        sourceChannel.read(buf);
        System.out.println(new String(buf.array(), 0, len));

        sourceChannel.close();
        sinkChannel.close();
    }
}
