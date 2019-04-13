/**
 * @author Tomaszewska Justyna S15313
 */

package zad1;

import javafx.application.Application;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        Application.launch(ClientApplication.class);
        Client c = new Client("");
        c.startClient();
        // TODO interaction between Client c and GUI
    }

    private String name;
    private Selector selector;
    private SocketChannel clientSocketChannel;
    private boolean isStopping;
    private List<IClientListener> listeners = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public void startClient() {

        try {
            this.selector = Selector.open();

            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 8090);
            clientSocketChannel = SocketChannel.open(hostAddress);
            clientSocketChannel.configureBlocking(false);
            clientSocketChannel.register(this.selector, SelectionKey.OP_READ);
            Client self = this;

            Runnable listenForMessage = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            self.selector.select();
                            if (isStopping) {
                                return;
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        Iterator keyIterator = self.selector.selectedKeys().iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey key = (SelectionKey) keyIterator.next();
                            keyIterator.remove();

                            if (!key.isValid()) {
                                continue;
                            }

                            if (key.isReadable()) {
                                try {
                                    this.read(key);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                }

                private void read(SelectionKey key) throws IOException {
                    SocketChannel clientSocketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int numRead = -1;
                    numRead = clientSocketChannel.read(buffer);

                    if (numRead == -1) {
                        Socket socket = clientSocketChannel.socket();
                        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
                        System.out.println("Connection closed by server: " + remoteAddr);
                        clientSocketChannel.close();
                        key.cancel();
                        return;
                    }

                    byte[] data = new byte[numRead];
                    System.arraycopy(buffer.array(), 0, data, 0, numRead);
                    String msg = new String(data);
                    //System.out.println(name + " : get - message:" + msg);

                    for (IClientListener listener : listeners) {
                        listener.processMessage(msg);
                    }
                }
            };
            new Thread(listenForMessage).start();

            System.out.println("Client... started");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String msg) {
        byte[] message = msg.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(message);
        try {
            clientSocketChannel.write(buffer);
            System.out.println("client: " + name + " send:" + msg);
            buffer.clear();
        }
        catch (Exception e){
           throw new RuntimeException(e);
        }
    }

    public void addListener(IClientListener listener){
        listeners.add(listener);
    }

    public void disconnect() throws IOException {
        isStopping = true;
        clientSocketChannel.close();
        selector.wakeup();
    }
}