package SwordToOffer;

import java.io.*;
import java.net.Socket;

/**
 * 模拟传统NIO模型多线程处理不通Client请求,Client实现
 *
 * @author ggz on 2022/8/31
 */
public class BIOClientTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("客户端启动启动中...");
        Socket socket = new Socket("127.0.0.1", 1234);
        System.out.println("客户端启动成功...");
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String readline = "";

        while (!readline.equals("exit")) {

            readline = br.readLine();
            Student stu = new Student("ggz", "male");
            stu.setName(readline);
            objectOutputStream.writeObject(stu);
            objectOutputStream.flush();
            System.out.println("NIOClient:" + stu);

//            readline = br.readLine();
//            printWriter.println(readline);
//            printWriter.flush();
//            System.out.println("NIOClient:" + readline);
        }
        printWriter.close();
        socket.close();
    }
}