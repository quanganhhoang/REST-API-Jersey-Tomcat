package edu.neu.cs.cs6650.servlet;

import edu.neu.cs.cs6650.controller.ResortService;

import edu.neu.cs.cs6650.model.ResortList;
import edu.neu.cs.cs6650.model.SeasonList;
import edu.neu.cs.cs6650.model.Stat;
import java.sql.SQLException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

@Path("/resorts")
public class ResortServlet {
  private static final Logger logger = LogManager.getLogger(ResortServlet.class.getName());
  private static final String BAD_REQUEST_MSG = "{message: bad_request}";

  private ResortService resortService = new ResortService();

  /**
   *
   * @return
   */
  @GET // annotate method with http annotation
  @Produces(MediaType.APPLICATION_JSON) // specify response format
  public Response getResorts() {
    logger.info("Retrieving all resorts...");
    try {
      ResortList resorts = resortService.getAllResorts();

      GenericEntity<ResortList> myEntity = new GenericEntity<ResortList>(resorts){};
      CacheControl cc = new CacheControl(); // public by default
      cc.setMaxAge(10);
      // start separate thread to log server response stat
      return Response.ok(myEntity)
          .type(MediaType.APPLICATION_JSON)
          .cacheControl(cc)
          .build();
    } catch (SQLException e) {
      logger.info("ERROR: ", e);
      return Response.status(Status.BAD_REQUEST).entity(BAD_REQUEST_MSG).build();
    }
  }

  /**
   * Get all seasons by resortId.
   * @param resortId
   * @return
   */
  @GET // annotate method with http annotation
  @Path("/{resortId}/seasons")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON) // specify response format
  public Response getSeasonsByResort(@PathParam("resortId") int resortId) {
    logger.info("Retrieving all seasons by resort...");
    try {
      SeasonList seasons = resortService.getSeasonsByResort(resortId);
      GenericEntity<SeasonList> myEntity = new GenericEntity<SeasonList>(seasons){};
      return Response.ok(myEntity)
          .type(MediaType.APPLICATION_JSON)
          .build();
    } catch (SQLException e) {
      logger.info("ERROR: ", e);
      return Response.status(Status.BAD_REQUEST).entity(BAD_REQUEST_MSG).build();
    }
  }

  /**
   * Add a new season to a resort.
   * @param resortId
   * @return
   */
  @POST // annotate method with http annotation
  @Path("/{resortId}/seasons")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON) // specify response format
  public Response addSeasonByResort(String req, @PathParam("resortId") int resortId) {
    JSONObject req_body = new JSONObject(req);
    String season = req_body.getString("year");

    try {
      boolean success = resortService.addNewSeason(resortId, season);
      return success ? Response.status(Status.CREATED).build()
                     : Response.status(Status.BAD_REQUEST).entity(BAD_REQUEST_MSG).build();
    } catch (SQLException e) {
      logger.info("ERROR: ", e);
      return Response.status(Status.BAD_REQUEST).entity(BAD_REQUEST_MSG).build();
    }
  }
}
