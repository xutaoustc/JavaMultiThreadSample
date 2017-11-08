package advanced.a5_nio_aio.echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.locks.LockSupport;

public class Client {
    private static final int sleep_time = 1000 * 1000 * 1000;

    public static void main(String[] args) throws IOException {

        Socket client = null;
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            client = new Socket();
            client.connect(new InetSocketAddress("localhost", 8000));
            writer = new PrintWriter(client.getOutputStream(), true);
            writer.print("H");
            LockSupport.parkNanos(sleep_time);
            writer.print("e");
            LockSupport.parkNanos(sleep_time);
            writer.print("l");
            LockSupport.parkNanos(sleep_time);
            writer.print("l");
            LockSupport.parkNanos(sleep_time);
            writer.print("o");
            LockSupport.parkNanos(sleep_time);
            writer.print("!");
            LockSupport.parkNanos(sleep_time);
            writer.println();
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("from server: " + reader.readLine());
        } catch (Exception e) {
        } finally {
            writer.close();
            reader.close();
            client.close();
        }
    }
}
