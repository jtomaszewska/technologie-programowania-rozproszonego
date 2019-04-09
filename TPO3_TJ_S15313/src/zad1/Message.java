package zad1;

import java.io.Serializable;

public class Message implements Serializable {
    MessageType messageType;
    String sender;
    String messageText;
}
