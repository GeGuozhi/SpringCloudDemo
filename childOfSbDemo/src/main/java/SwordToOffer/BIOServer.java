package SwordToOffer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟传统NIO模型多线程处理不通Client请求,服务端实现
 *
 * @author ggz on 2022/8/30
 */
public class BIOServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ServerSocket server = new ServerSocket(1234);

        while (true) {
            executorService.submit(new ConnectionTask(server));
        }
    }
}

class ConnectionTask extends Thread {

    Socket socket = new Socket();

    public ConnectionTask(ServerSocket server) throws IOException {
        socket = server.accept();
    }

    public void run() {
        System.out.println("服务器连接成功...");
        ObjectInputStream os = null;
        try {
            os = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Student stu = null;
            try {
                stu = (Student) os.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("服务器接收到消息:" + stu);
        }
    }
}
