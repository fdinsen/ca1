/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.JokeDTO;
import entities.Joke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Oliver
 */
public class JokeFacade {
    private static JokeFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private JokeFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static JokeFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new JokeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public long getJokeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long jokeCount = (long)em.createQuery("SELECT COUNT(j) FROM Joke j").getSingleResult();
            return jokeCount;
        }finally{  
            em.close();
        }
    }
    
    public JokeDTO getJokeById(EntityManagerFactory _emf, int id) {
        EntityManager em = _emf.createEntityManager();
        Joke joke = em.find(Joke.class,id);
        if(joke == null) return null;
        return convertToDto(joke);
    }
    
    public List<JokeDTO> getAllJokes(EntityManagerFactory _emf) {
        EntityManager em = _emf.createEntityManager();
        Query query = em.createQuery("Select j FROM Joke j");
        List<Joke> jokes = query.getResultList();
        List<JokeDTO> jokeDTOs = new ArrayList<>();
        jokes.forEach(joke -> {
            jokeDTOs.add(convertToDto(joke));
        });
        return jokeDTOs;
    }
    
    public JokeDTO createJoke(EntityManagerFactory _emf, String joke, String type) {
        EntityManager em = _emf.createEntityManager();
        Joke joken = new Joke(joke, type);
        try {
            em.getTransaction().begin();
            em.persist(joken);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return convertToDto(joken);
    }
    
     public JokeDTO getRandomJoke(EntityManagerFactory _emf) {
        EntityManager em = _emf.createEntityManager();
        
        long jokeCount = (long)em.createQuery("SELECT COUNT(j) FROM Joke j").getSingleResult();
        
         Random random = new Random();
         int rndNumber = random.nextInt((int)jokeCount);
  
        Joke joke = em
        .createQuery(
            "SELECT j FROM Joke j", Joke.class).setFirstResult(rndNumber).setMaxResults(1).getSingleResult();
        return convertToDto(joke);
    }
    
    private JokeDTO convertToDto(Joke joke) {
        JokeDTO jokeDTO = new JokeDTO(joke);
        return jokeDTO;
    }
}
