package site.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;

import site.entities.Task;

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
    public List<Task> findByTasks(String title, Integer lista, Integer status) {
        
        if(lista == null) {
            return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus "
                    + "ORDER BY -t.dtLimit DESC")
                    .setParameter("tTitle", "%"+title+"%")
                    .setParameter("tStatus", status)
                    .getResultList();
        } else {
            return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus "
                    + "AND t.lista.idLista = :tLista ORDER BY -t.dtLimit DESC")
                    .setParameter("tTitle", "%"+title+"%")
                    .setParameter("tStatus", status)
                    .setParameter("tLista", lista)
                    .getResultList();
        }
        
        
    }
    
    @SuppressWarnings("unchecked")
    public List<Task> findByTasks(String title, Integer lista, Integer status, Integer order) {
        
        if(lista == null) {
            return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus "
                    + "ORDER BY -t.dtComplete")
                    .setParameter("tTitle", "%"+title+"%")
                    .setParameter("tStatus", status)
                    .getResultList();
        } else {
            return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus "
                    + "AND t.lista.idLista = :tLista ORDER BY -t.dtComplete")
                    .setParameter("tTitle", "%"+title+"%")
                    .setParameter("tStatus", status)
                    .setParameter("tLista", lista)
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

}
