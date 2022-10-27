package site.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

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
    
    public List<Task> findAll() {
        return this.em.createQuery("SELECT t FROM Task t").getResultList();
    }
    
    public List<Task> findByTitleStatus(String title, Integer status) {
        return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus ORDER BY -t.dtLimit DESC")
                .setParameter("tTitle", "%"+title+"%")
                .setParameter("tStatus", status)
                .getResultList();
    }
    
    public List<Task> findByTitleStatus(String title, Integer status, Integer order) {
        return this.em.createQuery("SELECT t FROM Task t WHERE t.title LIKE :tTitle AND t.status = :tStatus ORDER BY -t.dtComplete")
                .setParameter("tTitle", "%"+title+"%")
                .setParameter("tStatus", status)
                .getResultList();
    }
    
    public Task save(Task entity) {
        
        Task task = this.find(entity.getIdTask());
        
        if (task != null) {
            try {
                BeanUtils.copyProperties(task, entity);
                this.em.merge(task);
                return task;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            this.em.persist(entity);
            return entity;
        }
    }

}
