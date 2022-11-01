package site.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.commons.beanutils.BeanUtils;

import site.entities.Lista;

public class ListaDAO {
    
    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    public ListaDAO() {
        super();
    }
    
    public Lista find(Integer id) {
        return this.em.find(Lista.class, id);
    }
    
    @SuppressWarnings("unchecked")
    public List<Lista> findAll() {
        return this.em.createQuery("SELECT l FROM Lista l ORDER BY l.title").getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Lista> findByTitle(String title) {
        return this.em.createQuery("SELECT t FROM Lista t WHERE t.title LIKE :tTitle ORDER BY -t.title")
                .setParameter("tTitle", "%"+title+"%")
                .getResultList();
    }
    
    public Lista save(Lista entity) {
        
        Lista Lista = this.find(entity.getIdLista());
        
        if (Lista != null) {
            try {
                BeanUtils.copyProperties(Lista, entity);
                this.em.merge(Lista);
                return Lista;
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
