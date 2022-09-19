package SwordToOffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NIO是面向缓冲区的
 */
public class NIOServer {

    private Selector selector;
    /**
     * 值得注意的是 Buffer 及其子类都不是线程安全的。
     */
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);//设置缓冲区大小

    /**
     * 启动方法
     *
     * @throws IOException
     */
    public void start() throws Exception {

        //创建服务端的channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //非阻塞方式
        serverSocketChannel.configureBlocking(false);

        //绑定IP
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8080));

        //创建选择器
        selector = Selector.open();

        //注册监听事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (!Thread.currentThread().isInterrupted()) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                System.out.println("有监听的socket收到信息啦");
                SelectionKey key = keyIterator.next();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                }
                //去除本次keyIterator.next()的对象,但不会对下次遍历有影响
                keyIterator.remove();
            }
        }
    }

    /**
     * 读入
     *
     * @param key
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        this.readBuffer.clear();
        int numRead;
        try {
            /**
             * 获取客户点的read操作读入数据块数量
             */
            numRead = socketChannel.read(readBuffer);
        } catch (Exception e) {
            key.cancel();
            socketChannel.close();
            return;
        }
        if (numRead > 0) {
            //接收客户端信息,下面3个获取客户端IP的方法，可自行选择
            String recMsg = new String(readBuffer.array(), 0, numRead);
            System.out.println("收到客户端 " + socketChannel.getLocalAddress() + "信息:" + recMsg);

            //返回客户端信息
            String sendMsg = "服务端已收到信息:" + recMsg;
            ByteBuffer writeBuffer = ByteBuffer.wrap(sendMsg.getBytes());
            socketChannel.write(writeBuffer);
        } else {
            System.out.println("客户端断开连接：" + socketChannel.getLocalAddress());
        }
    }

    /**
     * 接收
     *
     * @param key
     * @throws IOException
     */
    public void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel
                = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("成功连接客户端：" + serverSocketChannel.getLocalAddress());
    }

    public static void main(String[] args) throws Exception {
        new NIOServer().start();
    }
}