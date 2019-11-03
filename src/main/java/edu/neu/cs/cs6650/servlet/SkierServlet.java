package edu.neu.cs.cs6650.servlet;

import edu.neu.cs.cs6650.controller.SkierService;
import edu.neu.cs.cs6650.model.SeasonVerticalList;
import java.sql.SQLException;
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

@Path("/skiers")
public class SkierServlet {
  private static final Logger logger = LogManager.getLogger(SkierServlet.class.getName());
  private SkierService skierService = new SkierService();
  private static final String BAD_REQUEST_MSG = "{message: bad_request}";

  /** Get total vertical for the given skier for the specified ski day.
   *
   * @return
   */
  @GET // annotate method with http annotation
  @Path("/{resortId}/seasons/{seasonId}/days/{dayId}/skiers/{skierId}")
  @Produces(MediaType.APPLICATION_JSON) // specify response format
  public Response getVertical(@PathParam("resortId") int resortId, @PathParam("seasonId") String seasonId,
      @PathParam("dayId") int dayId, @PathParam("skierId") int skierId) {

    try {
      String res = String.valueOf(skierService.getVertical(resortId, seasonId, dayId, skierId));

      return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(res).build();
    } catch (SQLException e) {
      logger.info("ERROR: ", e);

      return Response.status(Status.BAD_REQUEST).entity(BAD_REQUEST_MSG).build();
    }
  }

  /** Stores new lift ride details in database
   *
   * @return
   */
  @POST // annotate method with http annotation
  @Path("/{resortId}/seasons/{seasonId}/days/{dayId}/skiers/{skierId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON) // specify response format
  public Response addLiftRide(String req, @PathParam("resortId") int resortId, @PathParam("seasonId") String season,
      @PathParam("dayId") int dayId, @PathParam("skierId") int skierId) {

    JSONObject req_body = new JSONObject(req);
    int time = req_body.getInt("time");
    int liftId = req_body.getInt("liftID");
    int vertical = liftId * 10;

    boolean res;
    try {
      res = skierService.postVertical(resortId, season, dayId, skierId, time, liftId, vertical);

      if (res) return Response.status(Status.CREATED).build();
      else return Response.status(Status.BAD_REQUEST).build();
    } catch (SQLException e) {
      logger.info("ERROR: ", e);

      return Response.status(Status.BAD_REQUEST).entity(BAD_REQUEST_MSG).build();
    }
  }

  @GET
  @Path("/{skierId}/vertical")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTotalVerticalByResort(String req, @PathParam("skierId") int skierId) {

    JSONObject req_body = new JSONObject(req);
    String resortName = req_body.getString("resort");
    String season = req_body.has("season") ? req_body.getString("season") : null;

    try {
      if (season == null) {
        SeasonVerticalList res = skierService.getTotalVerticalByResort(skierId, resortName);
        return Response.status(Status.OK)
             .entity(new GenericEntity<SeasonVerticalList>(res){})
             .type(MediaType.APPLICATION_JSON)
             .build();
      } else {
        String res = String.valueOf(skierService.getTotalVerticalByResort(skierId, resortName, season));
        return Response.status(Status.OK)
            .entity(res)
            .type(MediaType.APPLICATION_JSON)
            .build();
      }
    } catch (SQLException e) {
      logger.info("ERROR: ", e);

      return Response.status(Status.BAD_REQUEST).entity(BAD_REQUEST_MSG).build();
    }
  }
}
