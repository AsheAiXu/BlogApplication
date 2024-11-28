package dev.e23.BlogApplication.repository;

import dev.e23.BlogApplication.model.Comment;
import dev.e23.BlogApplication.util.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;

@ApplicationScoped
public class CommentRepository {
    public List<Comment> findByArticleId(Integer id) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        List<Comment> comments = null;
        try {
            em.getTransaction().begin();
            comments = em
                    .createQuery("SELECT c FROM Comment c WHERE c.articleId = :articleId", Comment.class)
                    .setParameter("articleId", id)
                    .getResultList();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException("", e);
        } finally {
            em.close();
        }
        return comments;
    }
    public void create(Comment comment) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(comment);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException("", e);
        } finally {
            em.close();
        }
    }
    public Comment findById(Long commentId) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        Comment comment = null;
        try {
            em.getTransaction().begin();
            comment = em.find(Comment.class, commentId);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error finding comment with ID " + commentId, e);
        } finally {
            em.close();
        }
        return comment;
    }
    public void delete(Long commentId) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Comment comment = em.find(Comment.class, commentId);
            if (comment != null) {
                em.remove(comment);
            } else {
                throw new RuntimeException("评论未找到");
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException("删除评论失败", e);
        } finally {
            em.close();
        }
    }
}
