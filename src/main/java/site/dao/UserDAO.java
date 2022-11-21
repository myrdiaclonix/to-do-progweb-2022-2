package site.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import site.entities.User;

@Stateless
public class UserDAO {
    
    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    public UserDAO() {
        super();
    }
    
    public User find(Integer id) {
        if(id != null && id > 0) {
            return (User) this.em.createQuery(
                    "SELECT u FROM User u WHERE u.id = :uId")
                    .setParameter("uId", id)
                    .getResultList().get(0);
        } else {
            return null;
        }
    }
    
    public List<User> findAll() {
        return this.em.createQuery("SELECT u FROM User u").getResultList();
    }
    
    public List<User> listByEmail(String email) {
        if(email != null && !email.isEmpty()) {
            return this.em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :uEmail")
                    .setParameter("uEmail", email)
                    .getResultList();
        } else {
            return null;
        }
    }
    
    public boolean Save(User Obj) { 
        
        boolean res = false;
        EntityTransaction trx = this.em.getTransaction();

        try {
            trx.begin();
            
            User us = find(Obj.getId());
            
            if(us == null) {
                this.em.persist(Obj);
                this.em.flush();  
            } else {
                this.em.createQuery(
                        "UPDATE User u SET u.email = :uEmail, password = :uPass "
                        + "WHERE u.id = :uId")
                        .setParameter("uEmail", Obj.getEmail())
                        .setParameter("uPass", Obj.getPassword())
                        .setParameter("uId", Obj.getId());
            }
            
            trx.commit();
            res = true;
            
        } catch (Exception e) {
            trx.rollback();
        }
        
        return res;
    }

}
