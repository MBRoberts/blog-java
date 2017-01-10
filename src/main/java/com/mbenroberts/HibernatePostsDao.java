package com.mbenroberts;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;

import java.util.List;

public class HibernatePostsDao implements Posts {

    private Session session;

    public HibernatePostsDao(Session session) {
        this.session = session;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Post> all(int page) {

        int pageSize = 2;
        int count = Integer.parseInt(getPostsCount().toString());

        Criteria criteria = session.createCriteria(Post.class);
        criteria.setFirstResult(count - (pageSize * page));
        criteria.setMaxResults(pageSize);

        return criteria.list();
    }

    @Override
    public void save(Post post) {
        Transaction tx = session.beginTransaction();
        session.save(post);
        tx.commit();
    }

    @Override
    public Long getPostsCount(){

        Criteria criteriaCount = session.createCriteria(Post.class);
        criteriaCount.setProjection(Projections.rowCount());

        return (Long) criteriaCount.uniqueResult();
    }

    @Override
    public Post getPostById(Long id){
        Query query = session.createQuery("FROM Post WHERE id = :id");
        query.setParameter("id", id);

        Post post = (Post) query.getSingleResult();

        return post;
    }

    @Override
    public void update(Post post){
        Post existingPost = getPostById(post.getId());
        existingPost.setTitle(post.getTitle());
        existingPost.setBody(post.getBody());
        Transaction tx = session.beginTransaction();
        session.update(existingPost);
        tx.commit();
    }
}
