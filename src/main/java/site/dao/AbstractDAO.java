package site.dao;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class AbstractDAO<T> {

    public AbstractDAO() {
        
    }

    protected abstract EntityManager getEntityManager();

    // Insere um objeto T na tabela do BD (create)
    public void create(T entity) {

    }

    // Atualiza um objeto T na tabela do BD (update)
    public void edit(T entity) {

    }

    // Exclui um objeto T da tabela do BD (delete)
    public void remove(T entity) {

    }

    // Busca um objeto T pelo id (retrive)
    public T find(Object id) {
        return null;
    }

    // Busca todos os objetos T (retrive)
    public List<T> findAll() {
        return null;
    }

    // Busca os objeto T por intervalo (retrive)
    public List<T> findRange(Integer[] range) {
        return null;
    }

    // Busca os objeto T por intervalo (retrive)
    public Integer count() {
        return 0;
    }
    
}
