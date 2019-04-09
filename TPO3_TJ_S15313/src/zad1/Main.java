/**
 *
 *  @author Tomaszewska Justyna S15313
 *
 */

package zad1;

import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    //Client c1 = new Client();
    //c1.startClient();

    Runnable server = new Runnable() {
      @Override
      public void run() {
        try {
          new Server("localhost", 8090).startServer();
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println("Server exit");
      }
    };

    Runnable client = new Runnable() {
      @Override
      public void run() {
        new Client().startClient();
      }
    };
    new Thread(server).start();
    new Thread(client, "client-A").start();
    new Thread(client, "client-B").start();
  }

}
