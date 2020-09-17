/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.GroupMemberDTO;
import entities.GroupMember;
import entities.RenameMe;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

/**
 *
 * @author gamma
 */
public class GroupFacadeTest {
    private static EntityManagerFactory emf;
    private static GroupFacade facade;
    
    private GroupMember gm1;
    private GroupMember gm2;
    private GroupMember gm3;

    public GroupFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = GroupFacade.getGroupFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        gm1 = new GroupMember("Peter", "pe12", "Twin Peaks", "Aerosmith", 1990, "Lyngby");
        gm2 = new GroupMember("Lise", "li32", "Brooklyn Nine-Nine", "Snow Patrol", 1996, "Buddinge");
        gm3 = new GroupMember("James", "ja16", "The Good Place", "Syd Matters", 1995, "Bagsvaerd");
        EntityManager em = emf.createEntityManager();
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

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }


    @Test
    public void testGetAllMembersByCount() {
        //Arrange
        int expectedCount = 3;
        
        //Act
        List<GroupMemberDTO> actual = facade.getAllGroupMembers();
        
        //Assert
        assertEquals(expectedCount, actual.size());
    }
    
    @Test
    public void testGetAllMembersByContent() {
        //Act
        List<GroupMemberDTO> actual = facade.getAllGroupMembers();
        
        //Assert
        actual.forEach(gmdto -> {
            if(Objects.equals(gmdto.getId(), gm1.getId())) {
                assertEquals(gmdto.getName(), gm1.getName());
                assertEquals(gmdto.getStudentId(), gm1.getStudentId());
                assertEquals(gmdto.getFavouriteTvSeries(), gm1.getFavouriteTvSeries());
                assertEquals(gmdto.getFavouriteMusician(), gm1.getFavouriteMusician());
                assertEquals(gmdto.getYearOfBirth(), gm1.getYearOfBirth());
            }else if (Objects.equals(gmdto.getId(), gm2.getId())) {
                assertEquals(gmdto.getName(), gm2.getName());
                assertEquals(gmdto.getStudentId(), gm2.getStudentId());
                assertEquals(gmdto.getFavouriteTvSeries(), gm2.getFavouriteTvSeries());
                assertEquals(gmdto.getFavouriteMusician(), gm2.getFavouriteMusician());
                assertEquals(gmdto.getYearOfBirth(), gm2.getYearOfBirth());
            }else if (Objects.equals(gmdto.getId(), gm3.getId())) {
                assertEquals(gmdto.getName(), gm3.getName());
                assertEquals(gmdto.getStudentId(), gm3.getStudentId());
                assertEquals(gmdto.getFavouriteTvSeries(), gm3.getFavouriteTvSeries());
                assertEquals(gmdto.getFavouriteMusician(), gm3.getFavouriteMusician());
                assertEquals(gmdto.getYearOfBirth(), gm3.getYearOfBirth());
            }
        });
    }
    
    @Test
    public void testCreateMember() {
        //Arrange
        String expectedName = "Tobias";
        String expectedStudentId = "to91";
        String expectedTv = "Dragon Prince";
        String expectedMusic = "Thirty Seconds To Mars";
        int expectedBirthYear = 2000;
        String expectedCity = "Soborg";
        GroupMember actual;
        
        //Act
        GroupMemberDTO returnedDto = facade.createGroupMember(expectedName, expectedStudentId, expectedTv, expectedMusic, expectedBirthYear, expectedCity);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<GroupMember> query = em.createQuery("SELECT gm FROM GroupMember gm WHERE gm.id = :id", GroupMember.class);
            query.setParameter("id", returnedDto.getId());
            actual = query.getSingleResult();
            
        }finally {
            em.close();
        }
        
        //Assert
        assertEquals(expectedName, actual.getName());
        assertEquals(expectedStudentId, actual.getStudentId());
        assertEquals(expectedTv, actual.getFavouriteTvSeries());
        assertEquals(expectedMusic, actual.getFavouriteMusician());
        assertEquals(expectedBirthYear, actual.getYearOfBirth());
        assertEquals(expectedCity, actual.getCity());
    }

}
