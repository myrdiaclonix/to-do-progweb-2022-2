package site.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import site.entities.Tag;
import site.entities.User;

@Stateless
public class TagDAO {
    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    @SuppressWarnings("unchecked")
    public List<Tag> userTags(User u) {
        return this.em.createQuery("select t from Tag t where t.user.id = :user")
                .setParameter("user", u.getId())
                .getResultList();
    }
}