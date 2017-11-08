package advanced.a5_nio_aio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIO_CopyFile {

    public static void nioCopyFile(String resource, String destination) throws IOException {
        FileInputStream fis = new FileInputStream(resource);
        FileOutputStream fos = new FileOutputStream(destination);
        FileChannel readChannel = fis.getChannel();      //读文件通道
        FileChannel writeChannel = fos.getChannel();     //写文件通道

        ByteBuffer buffer = ByteBuffer.allocate(1024);   //读入数据缓存

        while (true) {
            buffer.clear();
            int len = readChannel.read(buffer);        //读入数据
            if (len == -1) {
                break;
                //读取完毕
            }
            buffer.flip();
            writeChannel.write(buffer); //写入文件
        }

        readChannel.close();
        writeChannel.close();
    }




    public static void main(String[]args) throws IOException {

    }
}
