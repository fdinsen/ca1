package rest;

import entities.Joke;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import static io.restassured.RestAssured.given;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isOneOf;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class JokeResourceTest {
    public JokeResourceTest() {
    }

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Joke j1, j2, j3,j4;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer(){
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        j1 = new Joke("Min Test Joke 1", "Joke Type 1");
        j2 = new Joke("Min Test Joke 2", "Joke Type 2");
        j3 = new Joke("Min Test Joke 3", "Joke Type 3");
        j4 = new Joke("Min Test Joke 4", "Joke Type 4");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.persist(j1);
            em.persist(j2);
            em.persist(j3);
            em.persist(j4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void logging() {
        given().log().all().when().get("/jokes/all").then().log().body();
    }

    @Test
    public void testIsJokeEndPointUp() {
        given().when().get("/jokes").then().statusCode(200);
    }

    @Test
    public void testGetAllSize() {
        given()
                .contentType("application/json")
                .get("/jokes/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("", hasSize(4));
    }

    @Test
    public void testGetAllOnJoke() {
        given()
                .contentType("application/json")
                .get("/jokes/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("joke", hasItem("Min Test Joke 3"));
    }

    @Test
    public void testGetJokeByID() {
        given()
                .contentType("application/json")
                .get("/jokes/" + j1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("joke", equalTo(j1.getJoke()));
    }

    @Test
    public void testCreateJoke() {
        String jokeText = "MinJoke Tekst";
        String jokeType = "MinJoke Type";
        given()
                .post("/jokes/" + jokeText + "/" + jokeType).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("joke", equalTo(jokeText)).and()
                .body("type", equalTo(jokeType));
    }

    @Test
    public void testGetRandomJoke() {
        //How to test getting a random??
        String jokeText = "MinJoke Tekst";
        String jokeType = "MinJoke Type";
        given()
                .get("/jokes/random").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("joke", isOneOf("Min Test Joke 1", "Min Test Joke 2","Min Test Joke 3","Min Test Joke 4"));
    }


}
