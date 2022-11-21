package site.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

// E
@Entity
@Table(name = "Listas_Shared")
public class ListaShared implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer idListaShared;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUser", referencedColumnName="id", nullable = false)  
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idLista", referencedColumnName="id", nullable = false)  
    private Lista lista;
    
    public ListaShared() {
    }
    
    public ListaShared(Integer id, User user, Lista lista) {
        this.idListaShared = id;
        this.user = user;
        this.lista = lista;
    }

    public Integer getIdListaShared() {
        return idListaShared;
    }

    public void setIdListaShared(Integer idListaShared) {
        this.idListaShared = idListaShared;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }
  
}
