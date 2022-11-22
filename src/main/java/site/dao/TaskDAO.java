package site.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import site.entities.Lista;
import site.entities.Task;

@Stateless
public class TaskDAO {
    
    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    public TaskDAO() {
        super();
    }
    
    public Task find(Integer id) {
        return this.em.find(Task.class, id);
    }
    
    @SuppressWarnings("unchecked")
    public List<Task> findAll() {
        return this.em.createQuery("SELECT t FROM Task t").getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Task> findAll(Integer idUser) {
        return this.em.createQuery("SELECT t FROM Task t WHERE t.user.id = :tUser")
                .setParameter("tUser", idUser)
                .getResultList();
        
    }
    
    @SuppressWarnings("unchecked")
    public List<Task> findByTasks(Integer idUser, String title, Integer lista, Integer status) {
        
        if(lista == null) {
            return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus "
                    + "AND t.user.id = :tUser "
                    + "ORDER BY -t.dtLimit DESC")
                    .setParameter("tTitle", "%"+title+"%")
                    .setParameter("tStatus", status)
                    .setParameter("tUser", idUser)
                    .getResultList();
        } else {
            return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus "
                    + "AND t.lista.idLista = :tLista "
                    + "AND t.user.id = :tUser "
                    + "ORDER BY -t.dtLimit DESC")
                    .setParameter("tTitle", "%"+title+"%")
                    .setParameter("tStatus", status)
                    .setParameter("tLista", lista)
                    .setParameter("tUser", idUser)
                    .getResultList();
        }
        
        
    }
    
    @SuppressWarnings("unchecked")
    public List<Task> findByTasks(Integer idUser, String title, Integer lista, Integer status, Integer order) {
        
        if(lista == null) {
            return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus "
                    + "AND t.user.id = :tUser "
                    + "ORDER BY -t.dtComplete")
                    .setParameter("tTitle", "%"+title+"%")
                    .setParameter("tStatus", status)
                    .setParameter("tUser", idUser)
                    .getResultList();
        } else {
            return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus "
                    + "AND t.lista.idLista = :tLista "
                    + "AND t.user.id = :tUser "
                    + "ORDER BY -t.dtComplete")
                    .setParameter("tTitle", "%"+title+"%")
                    .setParameter("tStatus", status)
                    .setParameter("tLista", lista)
                    .setParameter("tUser", idUser)
                    .getResultList();
        }
    }
    
    public Task save(Task t) {
        Task ts = null;
        EntityTransaction trn = this.em.getTransaction();
        trn.begin();
        try {
            this.em.merge(t);
            trn.commit();
            ts = t;
        } catch (Exception e) {
            trn.rollback();
        }
        return ts;
    }
    
    public boolean remove(Task t) {
        boolean ls = false;
        EntityTransaction trn = this.em.getTransaction();
        trn.begin();
        try {
            this.em.remove(t);
            trn.commit();
            ls = true;
        } catch (Exception e) {
            trn.rollback();
        }
        return ls;
    }

}
