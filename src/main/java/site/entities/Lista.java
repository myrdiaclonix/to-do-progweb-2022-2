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
@Table(name = "Listas")
public class Lista implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer idLista;
    
    @Column(name = "title", columnDefinition="varchar(128) NOT NULL")
    private String title;
    
    @Column(name = "description", nullable = true)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUser", referencedColumnName="id", nullable = false)  
    private User user;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="idLista", referencedColumnName="id", nullable = true)  
    private List<Task> tasks = new ArrayList<Task>();
    
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    @PreRemove
    public void nullTasks() {
        tasks.forEach(ts -> ts.setlista(null));
    }
}
