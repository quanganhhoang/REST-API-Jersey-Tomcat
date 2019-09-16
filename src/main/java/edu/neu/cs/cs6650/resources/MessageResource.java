package edu.neu.cs.cs6650.resources;

import edu.neu.cs.cs6650.controller.MessageService;
import edu.neu.cs.cs6650.model.Message;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/messages")
public class MessageResource {

  MessageService messageService = new MessageService();

  @GET
//  @Produces(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMessages() {
    List<Message> messages = messageService.getAllMessages();
    GenericEntity<List<Message>> myEntity = new GenericEntity<List<Message>>(messages){};
    return Response.status(200).entity(myEntity).build();
  }

  @GET
  @Path("/test") // mapping subsequent path to '/messages'
  @Produces(MediaType.TEXT_PLAIN)
  public String test() {
    return "tests";
  }

  /*


  @GET
  @Path("/{messageId}") // mapping variable subsequent path to '/messages'
  @Produces(MediaType.TEXT_PLAIN)
  public String test1(@PathParam("messageId") long messageId) {
    // inject {messageId} into variable messageId
    // jersey will try to convert messageId into a long
    return messageService.getMessage(messageId);
  }

  @GET
  @Path("/something/{messageId}/name/{id2}") // mapping variable subsequent path to '/messages'
  @Produces(MediaType.TEXT_PLAIN)
  public String test1(@PathParam("messageId") long messageId, @PathParam("id2") long id2) {
    // inject {messageId} into variable messageId
    // jersey will try to convert messageId into a long
    return messageService.getMessage(messageId);
  }


   */


}
