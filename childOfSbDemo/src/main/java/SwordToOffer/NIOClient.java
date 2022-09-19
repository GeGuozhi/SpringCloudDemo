package SwordToOffer;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextInitializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 */
public class NIOClient implements InitializingBean {

    /**
     * 启动开关
     *
     * @throws IOException
     */
    public void start() throws Exception {
        //通过静态工厂生产客户端的channnel
        SocketChannel socketChannel = SocketChannel.open();
        //设置客户端请求为非阻塞方式
        socketChannel.configureBlocking(false);
        //绑定IP
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        //创建选择器
        Selector selector = Selector.open();
        //注册监听事件
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        //键盘输入
        Scanner scanner = new Scanner(System.in);
        //单独开个线程让客户端输入信息到服务端
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("请输入信息!!");
                    String message = scanner.nextLine();
                    ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes());
                    socketChannel.write(writeBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                if (key.isConnectable()) {
                    socketChannel.finishConnect();
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端已经连上服务器端!!");
                    break;
                } else if (key.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(48);
                    byteBuffer.clear();
                    long byteRead = clientChannel.read(byteBuffer);
                    if (byteRead == -1) {
                        clientChannel.close();
                    } else {
                        byteBuffer.flip();
                        String receiveMsg = new String(byteBuffer.array(), 0, byteBuffer.limit());
                        System.out.println("接收来自服务器的消息：" + receiveMsg);
                    }

                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new NIOClient().start();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用 initializingBean 的 afterProperties()方法");
    }
}