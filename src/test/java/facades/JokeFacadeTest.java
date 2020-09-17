package facades;

import dto.JokeDTO;
import entities.Joke;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JokeFacadeTest {
    private static EntityManagerFactory emf;
    private static JokeFacade facade;
    private static Joke j1,j2,j3,j4;

    public JokeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = JokeFacade.getFacade(emf);
    }

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

    @AfterEach
    public void afterEach() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetJokeCount() {
        assertEquals(4, facade.getJokeCount(), "Expects four rows in the database");
    }

    @Test
    public void testGetJokeByIdCorrectID() {
        assertEquals(j2.getJoke(), facade.getJokeById(emf, j2.getId()).getJoke());
    }

    @Test
    public void testGetJokeByIdWrongID() {
        //Should return null
        assertEquals(null, facade.getJokeById(emf, 0));
    }


    @Test
    public void testGetAllJoke() {
        List<JokeDTO> jokes = facade.getAllJokes(emf);
        assertEquals(4, jokes.size());
    }


    @Test
    public void addCustomerTest() {
        String jokeText = "Her er joken";
        String jokeType = "Her er joketypen";

        JokeDTO joke = facade.createJoke(emf, jokeText,jokeType);
        assertEquals(jokeText, facade.getJokeById(emf, joke.getId()).getJoke());
    }
}
