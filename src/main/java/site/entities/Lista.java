package site.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// E
@Entity
@Table(name = "Listas")
public class Lista implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer idLista;
    
    @Column(name = "title", columnDefinition="varchar(128)", nullable = false)
    private String title;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="idUser", referencedColumnName="id", nullable = false)  
    private User user;
    
    public Lista() {
    }
    
    public Lista(Integer idLista, String title, String description) {
        this.idLista = idLista;
        this.title = title;
        this.description = description;
        this.user = null;
    }
    
    public Lista(Integer idLista, String title, String description, User user) {
        this.idLista = idLista;
        this.title = title;
        this.description = description;
        this.user = user;
    }
    
    public Integer getIdLista() {
        return idLista;
    }

    public void setIdLista(Integer idLista) {
        this.idLista = idLista;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
