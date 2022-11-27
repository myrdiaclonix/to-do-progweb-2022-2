package site.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import site.entities.Lista;
import site.entities.ListaShared;

@Stateless
public class ListaDAO {
    
    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    @EJB
    private ListaSharedDAO daoListaShared;
    
    public ListaDAO() {
        super();
    }
    
    public Lista find(Integer id) {
        return this.em.find(Lista.class, id);
    }
    
    public Lista findById(Integer id, Integer idUser) {
        Lista ls = find(id);
        
        List<ListaShared> lists = daoListaShared.findByUserList(idUser, id);
        
        return ls != null && (ls.getUser().getId() == idUser || lists.size() == 1) ? ls : null;
    }
    
    @SuppressWarnings("unchecked")
    public List<Lista> findAll() {
        return this.em.createQuery("SELECT l FROM Lista l ORDER BY l.title").getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Lista> findAll(Integer idUser) {
        return this.em.createQuery("SELECT l FROM Lista l WHERE l.user.id = :tUser ORDER BY l.title")
                .setParameter("tUser", idUser)
                .getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Lista> findByTitle(String title) {
        return this.em.createQuery("SELECT t FROM Lista t WHERE t.title LIKE :tTitle ORDER BY -t.title")
                .setParameter("tTitle", "%"+title+"%")
                .getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Lista> findByTitle(String title, Integer idUser) {
        return this.em.createQuery("SELECT t FROM Lista t WHERE t.title LIKE :tTitle AND t.user.id = :tUser ORDER BY -t.title")
                .setParameter("tTitle", title)
                .setParameter("tUser", idUser)
                .getResultList();
    }
    
    public Lista save(Lista l) {
        Lista ls = null;
        EntityTransaction trn = this.em.getTransaction();
        trn.begin();
        try {
            this.em.merge(l);
            trn.commit();
            ls = l;
        } catch (Exception e) {
            trn.rollback();
        }
        return ls;
    }
    
    public boolean remove(Lista l) {
        boolean ls = false;
        EntityTransaction trn = this.em.getTransaction();
        trn.begin();
        try {
            this.em.remove(l);
            trn.commit();
            ls = true;
        } catch (Exception e) {
            trn.rollback();
        }
        return ls;
    }
    
    /*
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
    */
    
}
