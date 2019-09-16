package edu.neu.cs.cs6650.controller;

import edu.neu.cs.cs6650.model.Message;
import java.util.Arrays;
import java.util.List;

public class MessageService {

  public List<Message> getAllMessages() {
    Message m1 = new Message(1, "hi", "alex");
    Message m2 = new Message(2, "hi", "joe");

    return Arrays.asList(m1, m2);
  }
}
