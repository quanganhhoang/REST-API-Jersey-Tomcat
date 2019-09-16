package edu.neu.cs.cs6650.resources;

import edu.neu.cs.cs6650.controller.MessageService;
import edu.neu.cs.cs6650.model.Message;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/messages/test")
public class Test {

  MessageService messageService = new MessageService();

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getMessages() {
    return "TEST";
  }
}
