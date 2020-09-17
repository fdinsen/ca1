/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.GroupMemberDTO;
import entities.GroupMember;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author gamma
 */
public class GroupFacade {
    private static GroupFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private GroupFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static GroupFacade getGroupFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GroupFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public GroupMemberDTO createGroupMember(String name, String studentId, String favouriteTvSeries, String favouriteMusician, int yearOfBirth, String city) {
        EntityManager em = getEntityManager();
        try {
            GroupMember member = new GroupMember(name, studentId, favouriteTvSeries, favouriteMusician, yearOfBirth, city);
            em.getTransaction().begin();
            em.persist(member);
            em.getTransaction().commit();
            return new GroupMemberDTO(member);
        }finally {
            em.close();
        }
    }
    
    public List<GroupMemberDTO> getAllGroupMembers() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<GroupMember> query = em.createQuery("SELECT gm FROM GroupMember gm", GroupMember.class);
            List<GroupMemberDTO> list = new ArrayList();
            query.getResultList().stream().forEach(mem -> list.add(new GroupMemberDTO(mem)));
            return list;
        }finally{
            em.close();
        }
    }
}
