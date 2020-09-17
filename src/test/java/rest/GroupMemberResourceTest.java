/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.GroupMember;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

/**
 *
 * @author gamma
 */
public class GroupMemberResourceTest {
    
    public GroupMemberResourceTest() {
    }

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static GroupMember gm1, gm2, gm3;
    
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
        gm1 = new GroupMember("Peter", "pe12", "Twin Peaks", "Aerosmith", 1990, "Lyngby");
        gm2 = new GroupMember("Lise", "li32", "Brooklyn Nine-Nine", "Snow Patrol", 1996, "Buddinge");
        gm3 = new GroupMember("James", "ja16", "The Good Place", "Syd Matters", 1995, "Bagsvaerd");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("GroupMember.deleteAllRows").executeUpdate();
            em.persist(gm1);
            em.persist(gm2);
            em.persist(gm3);
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
    }
    
    @Test
    public void logging() {
        given().log().all().when().get("/groupmembers/all").then().log().body();
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/groupmembers").then().statusCode(200);
    }
    
    @Test
    public void testGetAllOnSize() {
        given()
         .contentType("application/json")
         .get("/groupmembers/all").then()
         .assertThat()
         .statusCode(HttpStatus.OK_200.getStatusCode())
         .body("", hasSize(3));        
    }
    
    @Test
    public void testGetAllOnContent() {
        given()
         .contentType("application/json")
         .get("/groupmembers/all").then()
         .assertThat()
         .statusCode(HttpStatus.OK_200.getStatusCode())
         .body("name", hasItem("James")); 
    }
    
    @Test
    public void testAddMemberOnDTO() {
        String parameters 
                = "?name=Karl" 
                + "&sid=ka55" 
                + "&favtv=Lucifer"
                + "&favmus=GunsAndRoses"
                + "&year=1995"
                + "&city=herlev";
        given()
         .contentType("application/json")
         .get("groupmembers/add" + parameters).then()
         .assertThat()
         .statusCode(HttpStatus.OK_200.getStatusCode())
         .body("name", equalTo("Karl")).and()
         .body("studentId", equalTo("ka55")).and()
         .body("favouriteTvSeries", equalTo("Lucifer")).and()
         .body("favouriteMusician", equalTo("GunsAndRoses")).and()
         .body("yearOfBirth", equalTo(1995));
    }
}
