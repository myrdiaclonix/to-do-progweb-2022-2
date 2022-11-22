package site.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import site.entities.Lista;
import site.entities.ListaShared;

@Stateless
public class ListaSharedDAO {
    
    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    public ListaSharedDAO() {
        super();
    }
    
    public ListaShared find(Integer id) {
        return this.em.find(ListaShared.class, id);
    }
    
    public ListaShared findById(Integer id, Integer idUser) {
        ListaShared ls = find(id);
        return ls != null && ls.getUser().getId() == idUser ? ls : null;
    }
    
    @SuppressWarnings("unchecked")
    public List<ListaShared> findAll() {
        return this.em.createQuery("SELECT l FROM ListaShared l").getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<ListaShared> findAll(Integer idUser) {
        return this.em.createQuery("SELECT l FROM ListaShared l"
                + "INNER JOIN User u ON u.user.id = :lUser")
                .setParameter("lUser", idUser)
                .getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<ListaShared> findByTitle(String title) {
        return this.em.createQuery("SELECT t FROM Lista t WHERE t.title LIKE :tTitle ORDER BY -t.title")
                .setParameter("tTitle", "%"+title+"%")
                .getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<ListaShared> findByTitle(String title, Integer idUser) {
        return this.em.createQuery("SELECT t FROM Lista t WHERE t.title LIKE :tTitle AND t.user.id = :tUser ORDER BY -t.title")
                .setParameter("tTitle", title)
                .setParameter("tUser", idUser)
                .getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<ListaShared> findByUserList(Integer idUser, Integer idList) {
        return this.em.createQuery("SELECT l FROM ListaShared l WHERE l.user.id = :lUser AND l.lista.idLista = :lList")
                .setParameter("lUser", idUser)
                .setParameter("lList", idList)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<ListaShared> findByUser(Integer idUser, Integer idUserC) {
        return this.em.createQuery("SELECT l FROM ListaShared l"
                + "INNER JOIN User u ON u.user.id = :lUser "
                + "WHERE l.user.id = :lUserC")
                .setParameter("lUser", idUser)
                .setParameter("lUserC", idUserC)
                .getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<ListaShared> findByLista(Integer idLista) {
        return this.em.createQuery("SELECT l FROM ListaShared l "
                + "WHERE l.lista.idLista = :lLista")
                .setParameter("lLista", idLista)
                .getResultList();
    }
    
    public ListaShared save(ListaShared l) {
        ListaShared ls = null;
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
    
    public boolean remove(ListaShared l) {
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
