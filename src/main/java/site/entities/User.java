package site.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

	@Column(name = "name", nullable = false)
    private String name;
	
	@Column(name = "email", nullable = false)
    private String email;

	@Column(name = "password", nullable = false)
    private String password;
	
	@Column(name = "created_at", nullable = true, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    public User() {
		super();
	}
	
	public User(Integer id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
        this.email = email;
	}
	
	public User(Integer id, String name, String email, String password) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
 
}
