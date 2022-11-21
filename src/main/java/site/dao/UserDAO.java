package site.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import site.entities.Lista;
import site.entities.Task;
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
        
        Boolean ls = null;
        EntityTransaction trn = this.em.getTransaction();
        trn.begin();
        try {
            this.em.merge(Obj);
            trn.commit();
            ls = true;
        } catch (Exception e) {
            trn.rollback();
        }
        return ls;
    }

}
