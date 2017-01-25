package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class represetning simple blog comment
 * @author Teo Toplak
 *
 */
@Entity
@Table(name="blog_comments")
@Cacheable(true)
public class BlogComment {

	/**
	 * comment id
	 */
	private Long id;
	/**
	 * entry it belongs to
	 */
	private BlogEntry blogEntry;
	/**
	 * mail of message user
	 */
	private String usersEMail;
	/**
	 * message text
	 */
	private String message;
	/**
	 * time when message was created
	 */
	private Date postedOn;
	
	/**
	 * id getter
	 * @return id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Id setter
	 * @param id id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Entry getter
	 * @return entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * entry setter
	 * @param blogEntry entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * User mail getter
	 * @return user mail
	 */
	@Column(nullable=false, length=100)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * user mail setter
	 * @param usersEMail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Message getter
	 * @return message text
	 */
	@Column(nullable=false, length=4096)
	public String getMessage() {
		return message;
	}

	/**
	 * Message text setter 
	 * @param message message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Posting time getter
	 * @return posting time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Posting time setter
	 * @param postedOn posting time to be set
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
