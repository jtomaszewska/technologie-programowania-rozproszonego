/**
 *
 *  @author Tomaszewska Justyna S15313
 *
 */

package zad1;


import javafx.application.Application;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Client {

  public static void main(String[] args) {
    Application.launch(ClientApplication.class);
    Client c  = new Client();
    c.startClient();
    //TODO interaction between Client c and GUI
  }

  private Selector selector;

  public void startClient() {

    try
    {
      this.selector = Selector.open();

      InetSocketAddress hostAddress = new InetSocketAddress ("localhost", 8090);
      SocketChannel clientSocketChannel = SocketChannel.open(hostAddress);
      clientSocketChannel.configureBlocking(false);
      clientSocketChannel.register(this.selector, SelectionKey.OP_READ);

      //TODO pętla nasłuchująca w innym wątku

      System.out.println("Client... started");

      String threadName = Thread.currentThread().getName();

      // Send messages to server
      String [] messages = new String[]
              {threadName + ": test1",threadName + ": test2",threadName + ": test3"};

      for (int i = 0; i < messages.length; i++) {
        byte [] message = new String(messages [i]).getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(message);
        clientSocketChannel.write(buffer);
        System.out.println(messages [i]);
        buffer.clear();
        Thread.sleep(5000);
      }
      clientSocketChannel.close();
    }
    catch (Exception e){
      throw new RuntimeException(e);
    }


  }
}
