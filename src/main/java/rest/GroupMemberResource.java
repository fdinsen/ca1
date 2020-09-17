package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.GroupMemberDTO;
import facades.GroupFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 *
 * @author gamma
 */
@Path("groupmembers")
public class GroupMemberResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final GroupFacade FACADE =  GroupFacade.getGroupFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllMembers() {
        List<GroupMemberDTO> list = FACADE.getAllGroupMembers();
        return Response.ok().entity(GSON.toJson(list)).build();
    }
    
   @Path("/add")
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response addMember(
           @QueryParam("name") String name, 
           @QueryParam("sid") String studentId,
           @QueryParam("favtv") String favouriteTv,
           @QueryParam("favmus") String favouriteMusic,
           @QueryParam("year") int yearOfBirth,
           @QueryParam("city") String city) {
       GroupMemberDTO createdMember = FACADE.createGroupMember(name, studentId, favouriteTv, favouriteMusic, yearOfBirth, city);
       return Response.ok().entity(GSON.toJson(createdMember)).build();
   }
}
