package advanced.a5_nio_aio;

import java.io.IOException;
import java.nio.ByteBuffer;

public class BufferTest {
    public static void main(String[]args) throws IOException {
        ByteBuffer b= ByteBuffer.allocate(15); //15个字节大小的缓冲区
        System.out.println("limit="+b.limit()+" capacity="+b.capacity()+" position="+b.position());


        for(int i=0;i<10;i++){ //存入10个字节数据
            b.put((byte)i);
        }
        System.out.println("limit="+b.limit()+" capacity="+b.capacity()+" position="+b.position());
        b.flip(); //重置position  , position设置成0， limit设置成实际大小， 通常用于读写转换
        System.out.println("limit="+b.limit()+" capacity="+b.capacity()+" position="+b.position());


        for(int i=0;i<5;i++){
            System.out.print(b.get());
        }
        System.out.println();
        System.out.println("limit="+b.limit()+" capacity="+b.capacity()+" position="+b.position());
        b.flip();
        System.out.println("limit="+b.limit()+" capacity="+b.capacity()+" position="+b.position());
    }
}
