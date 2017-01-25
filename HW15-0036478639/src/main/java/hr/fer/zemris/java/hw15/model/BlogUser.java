package hr.fer.zemris.java.hw15.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Class representing simple user
 * @author Teo Toplak
 *
 */
@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser {

	/**
	 * user id 
	 */
	private Long id;
	/**
	 * first name
	 */
	private String firstName;
	/**
	 * last name
	 */
	private String lastName;
	/**
	 * nickname
	 */
	private String nick;
	/**
	 * email
	 */
	private String email;
	/**
	 * password in hash
	 */
	private String passwordHash;
	/**
	 * entries this user created
	 */
	private List<BlogEntry> entries;
	
	
	
	/**
	 * entries getter
	 * @return the entries
	 */
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("createdAt")
	public List<BlogEntry> getEntries() {
		return entries;
	}
	/**
	 * entries setter
	 * @param entries the entries to set
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}
	/**
	 * id getter
	 * @return the id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	/**
	 * id setter
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * first name getter
	 * @return the firstName
	 */
	@Column
	public String getFirstName() {
		return firstName;
	}
	/**
	 * first name setter
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * last name getter
	 * @return the lastName
	 */
	@Column
	public String getLastName() {
		return lastName;
	}
	/**
	 * last name setter
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * nickname getter
	 * @return the nick
	 */
	@Column(unique=true)
	public String getNick() {
		return nick;
	}
	/**
	 * nickname setter
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	/**
	 * email getter
	 * @return the email
	 */
	@Column
	public String getEmail() {
		return email;
	}
	/**
	 * email setter
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * password getter
	 * @return the passwordHash
	 */
	@Column(nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}
	/**
	 * password setter
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	
}
