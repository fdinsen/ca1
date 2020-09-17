package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.JokeDTO;
import utils.EMF_Creator;
import facades.JokeFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("jokes")
public class JokeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final JokeFacade FACADE =  JokeFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String up() {
        return "Joke API Running";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCount() {
        long count = FACADE.getJokeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllJokes() {
        List<JokeDTO> jokes = FACADE.getAllJokes(EMF);
        if(jokes.isEmpty()) return new Gson().toJson("No Jokes Found");

        return new Gson().toJson(jokes);
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getJokeByID(@PathParam("id") int id) {
        JokeDTO joke = FACADE.getJokeById(EMF,id);
        if(joke == null) return new Gson().toJson("Joke not found");
        return new Gson().toJson(joke);
    }
    
    
    
    @Path("/{joke}/{type}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String createNewJoke(@PathParam("joke") String joke, @PathParam("type") String type) {
        JokeDTO joken = FACADE.createJoke(EMF, joke, type);
        return new Gson().toJson(joken);
    }
    
    @Path("/random")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRandomJoke() {
        JokeDTO joke = FACADE.getRandomJoke(EMF);
        if(joke == null) return new Gson().toJson("Joke not found");
        return new Gson().toJson(joke);
    }
    
}
