/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.CarDTO;
import entities.Car;
import entities.RenameMe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

/**
 *
 * @author simon
 */
public class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade facade;
    private Car c1;
    private Car c2;
    private Car c3;
    public CarFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CarFacade.getCarFacde(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        
        c1 = new Car(2020, "Ford","c1" , 50000, "john");
        c2 = new Car(1999, "Citron","c2" , 4000, "Pen");
        c3 = new Car(1920, "Honda","c3" , 200, "Mkkel");
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllMovies method, of class CarFacade.
     */
    @Test
    public void testGetAllMovies() {
        
        int expected = 3;
        
        int actual = facade.getAllMovies().size();
        
        assertEquals(expected, actual);

    }
    
    @Test
    public void testGetCarByID(){
        
        String exspected = "Ford";
        
        String actual = facade.getMovieByID(5).getMake();
        
        assertEquals(exspected, actual);
    }

}
