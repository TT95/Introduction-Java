package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;


import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",
			query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when",
			hints={@QueryHint(name="org.hibernate.cacheable", value="true")})
})

/**
 * Class representing simple blog entry
 * @author Teo Toplak
 *
 */
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * entry id
	 */
	private Long id;
	/**
	 * list of comments on this entry
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * time of creation
	 */
	private Date createdAt;
	/**
	 * time of modification
	 */
	private Date lastModifiedAt;
	/**
	 * title of entry
	 */
	private String title;
	/**
	 * entry text
	 */
	private String text;
	/**
	 * creator of this entry
	 */
	private BlogUser creator;


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
	 * creator getter
	 * @return the creator
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * creator setter
	 * @param creator the creator to set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * comments getter
	 * @return comments
	 */
	@OneToMany(mappedBy="blogEntry", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * comments setter 
	 * @param comments comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * creation time getter
	 * @return creation time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * creation time setter
	 * @param createdAt creation time
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * modification time getter
	 * @return modification time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * modification time setter
	 * @param lastModifiedAt modification time
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * title getter
	 * @return title
	 */
	@Column(nullable=false, length=40)
	public String getTitle() {
		return title;
	}

	/**
	 * title setter
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * text getter
	 * @return text
	 */
	@Column(nullable=false, length=4096)
	public String getText() {
		return text;
	}

	/**
	 * text setter
	 * @param text text
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
