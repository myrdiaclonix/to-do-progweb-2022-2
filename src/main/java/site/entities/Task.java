package site.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name = "tasks")
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer idTask;

	@Column(name = "title", columnDefinition="varchar(128)", nullable = false)
    private String title;

	@Column(name = "description", nullable = false)
    private String description;
	
	@Column(name = "status", columnDefinition="tinyint(1)", nullable = false)
    private Integer status;

    @Column(name = "dtLimit", nullable = true)
    private java.sql.Timestamp dtLimit;
    
    @Column(name = "dtComplete", nullable = true)
    private java.sql.Timestamp dtComplete;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="idUser", referencedColumnName="id", nullable = false)  
	private User user;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="idLista", referencedColumnName="id", nullable = false)  
    private Lista lista;
    
	public Task() {
	}

    public Task(Integer idTask, String title, String description, Integer status, Timestamp dtLimit) {
        this.idTask = idTask;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dtLimit = dtLimit;
        this.dtComplete = null;
        this.user = null;
        this.lista = null;
    }
    
    public Task(Integer idTask, String title, String description, Integer status, Timestamp dtLimit,
            Timestamp dtComplete, User user, Lista lista) {
        this.idTask = idTask;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dtLimit = dtLimit;
        this.dtComplete = dtComplete;
        this.user = user;
        this.lista = lista;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public java.sql.Timestamp getDtLimit() {
        return dtLimit;
    }

    public void setDtLimit(java.sql.Timestamp dtLimit) {
        this.dtLimit = dtLimit;
    }

    public java.sql.Timestamp getDtComplete() {
        return dtComplete;
    }

    public void setDtComplete(java.sql.Timestamp dtComplete) {
        this.dtComplete = dtComplete;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Lista getlista() {
        return lista;
    }

    public void setlista(Lista lista) {
        this.lista = lista;
    }
    
    public String getDtConvert(java.sql.Timestamp dt) {
        
        Date today = new Date();
        Locale local = new Locale("pt","BR");
        DateFormat formatDef = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy ' - ' hh:mm",local);
        DateFormat formatToday = new SimpleDateFormat("'HOJE - ' hh:mm ",local);
        DateFormat formatPen = new SimpleDateFormat("'ATRASADA - ' dd/MM/yyyy",local);
        DateFormat comparate = new SimpleDateFormat("ddMMyyyy",local);
        
        if(dt != null) {
            
            if(comparate.format(today).equals(comparate.format(dt.getTime()))) {
             
                return formatToday.format(dt.getTime());
            
            } else if(Integer.parseInt(comparate.format(today)) > Integer.parseInt(comparate.format(dt.getTime()))) {
               
                return formatPen.format(dt.getTime()); 
                
            } else {
                return formatDef.format(dt.getTime());
            }
        } 
        
        return null;
    }
    
}
