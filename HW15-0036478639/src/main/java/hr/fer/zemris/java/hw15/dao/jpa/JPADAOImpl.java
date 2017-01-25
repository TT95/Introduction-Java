package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.utility.Encrypt;

/**
 * JPA - DAO implementation. Contains methods for easy 
 * communication with database. Expects to have avaliable connections
 * in {@link JPAEMProvider} clas.
 * 
 * @author Teo Toplak
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public List<BlogUser> getAllUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = 
				(List<BlogUser>)em.createQuery("select b from BlogUser as b")
					.setHint("org.hibernate.cacheable", true)
					.getResultList();
			
		return users;
	}

	@Override
	public boolean isDuplicateNickname(String name) {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = 
				(List<BlogUser>)em.createQuery("select b from BlogUser as b where nick=:name")
					.setParameter("name", name)
					.setHint("org.hibernate.cacheable", true)
					.getResultList();
			
		if(users.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public void addUser(BlogUser user) {
		
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser blogUser = user;
		em.persist(blogUser);
		
	}

	@Override
	public boolean legalLogin(String nick, String password) {
		EntityManager em = JPAEMProvider.getEntityManager();
		String passwordHash = Encrypt.getHash(password);
		System.out.println(passwordHash+"----------------------------");
		try {
			em.createQuery("select b from BlogUser as b where nick=:name and passwordHash=:hash")
						.setParameter("name", nick)
						.setParameter("hash", passwordHash)
						.setHint("org.hibernate.cacheable", true)
						.getSingleResult();
		} catch(NoResultException e) {
			return false;
		}
		return true;
		
	}

	@Override
	public BlogUser getUser(String nick) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogUser blogUser = (BlogUser)em.createQuery(
				"select b from BlogUser as b where nick=:name")
		.setParameter("name", nick)
		.setHint("org.hibernate.cacheable", true)
		.getSingleResult();
		
		return blogUser;
	}
	
	@Override
	public BlogUser getUser(Long id) {
		return JPAEMProvider.getEntityManager().find(BlogUser.class, id); 
	}

	@Override
	public List<BlogEntry> getBlogEntries(String nick) {
		
		List<BlogEntry> entries = getUser(nick).getEntries();
			
		return entries;
	}

	@Override
	public void addComment(Long idLoggedInUser, String text, String entryId) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, idLoggedInUser);
		BlogEntry blogEntry = getBlogEntry(Long.parseLong(entryId));
		
		BlogComment blogComment = new BlogComment();
		blogComment.setBlogEntry(blogEntry);
		blogComment.setMessage(text);
		blogComment.setPostedOn(new Date());
		blogComment.setUsersEMail(blogUser.getEmail());
		
		blogEntry.getComments().add(blogComment);
		
		em.persist(blogComment);
		
	}

	@Override
	public void addBlogEntry(BlogEntry entry) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(entry);

	}

	@Override
	public void updateBlogEntry(String id, String title, String text) {
		EntityManager em = JPAEMProvider.getEntityManager();
		Query query=em.createQuery("UPDATE BlogEntry set lastModifiedAt = :lastModifiedAt,"
				+ "title = :title, text = :text WHERE id = :id ")
		.setParameter("id", Long.parseLong(id))
		.setParameter("lastModifiedAt", new Date())
		.setParameter("title", title)
		.setParameter("text", text)
		.setHint("org.hibernate.cacheable", true);
		
		query.executeUpdate();
	}




}
