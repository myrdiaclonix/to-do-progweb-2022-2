package site.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import site.entities.Usuario;

public class UsuarioDAO {
    
    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    public UsuarioDAO() {
        super();
    }
    
    public List<Usuario> listAll() {
        return this.em.createQuery("SELECT u FROM Usuario u").getResultList();
    }
    
    public List<Usuario> listById(Integer id) {
        if(id != null && id > 0) {
            return this.em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.id = :uId")
                    .setParameter("uId", id)
                    .getResultList();
        } else {
            return null;
        }
    }
    
    public List<Usuario> listByEmail(String email) {
        if(email != null && !email.isEmpty()) {
            return this.em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :uEmail")
                    .setParameter("uEmail", email)
                    .getResultList();
        } else {
            return null;
        }
    }
    
    public boolean Save(Usuario Obj) {
        
        boolean res = false;
        EntityTransaction trx = this.em.getTransaction();

        try {
            trx.begin();
            
            List<Usuario> us = listById(Obj.getId());
            
            if(us == null || us.size() == 0) {
                this.em.persist(Obj);
                this.em.flush();  
            } else {
                this.em.createQuery(
                        "UPDATE Usuario u SET u.email = :uEmail, password = :uPass "
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
