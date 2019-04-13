/**
 * @author Tomaszewska Justyna S15313
 */

package zad1;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {


        Server server = new Server("localhost", 8090);

        Client client1 = new Client("client 1");
        client1.addListener((msg) ->  System.out.println("client 1: otrzymalem wiadomosc " + msg));

        Client client2 = new Client("client 2");
        client2.addListener((msg) ->  System.out.println("client 2: otrzymalem wiadomosc " + msg));

        Thread serverThread = new Thread(() -> {
            server.startServer();
            System.out.println("Server exit");
        });
        serverThread.start();
        new Thread(() -> client1.startClient()).start();
        new Thread(() -> client2.startClient()).start();

        Thread.sleep(9000);

        client1.send("wiadomosc 1 ");
        client2.send("wiadomosc 2");

        Thread.sleep(5000);

        client1.disconnect();
        client2.disconnect();
        server.stop();

    }

}