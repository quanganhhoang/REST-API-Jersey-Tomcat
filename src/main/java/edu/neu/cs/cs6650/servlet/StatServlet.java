package edu.neu.cs.cs6650.servlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/statistics")
public class StatServlet {
  private static final Logger logger = LogManager.getLogger(StatServlet.class.getName());

  /**
   *
   * @return
   */
  @GET // annotate method with http annotation
  @Produces(MediaType.APPLICATION_JSON) // specify response format
  public String getStats() {
    // TODO
    return null;
//    GenericEntity<List<Resort>> myEntity = new GenericEntity<List<Resort>>(resorts){};
//    return Response.status(200).entity(myEntity).build();
  }
}
