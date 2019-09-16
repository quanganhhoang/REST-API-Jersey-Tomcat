package edu.neu.cs.cs6650.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloWorld {

  @GET // annotate method with http annotation
  @Produces(MediaType.TEXT_PLAIN) // specify response format
  public String getIt() {
    return "Got it!";
  }
}
