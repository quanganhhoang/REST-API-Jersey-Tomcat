package edu.neu.cs.cs6650.servlet;

import edu.neu.cs.cs6650.controller.StatService;
import edu.neu.cs.cs6650.model.StatList;

import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/statistics")
public class StatServlet {
  private static final Logger logger = LogManager.getLogger(StatServlet.class.getName());
  private static final String BAD_REQUEST_MSG = "{message: bad_request}";

  private StatService statService = new StatService();
  /**
   *
   * @return
   */
  @GET // annotate method with http annotation
  @Produces(MediaType.APPLICATION_JSON) // specify response format
  public Response getStats() {
    // get results from cache if data has not expired
    // else get from database

    // OR just get from database?
    logger.info("Retrieving statistics...");
    try {
      StatList stats = statService.getApiStats();
      GenericEntity<StatList> myEntity = new GenericEntity<StatList>(stats){};
      return Response.ok(myEntity)
          .type(MediaType.APPLICATION_JSON)
          .build();
    } catch (SQLException e) {
      logger.info("ERROR: ", e);
      return Response.status(Status.BAD_REQUEST).entity(BAD_REQUEST_MSG).build();
    }
  }
}
