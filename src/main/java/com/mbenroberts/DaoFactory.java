package com.mbenroberts;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DaoFactory {
    private static Posts postsDao;

    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session = sessionFactory.openSession();

    public static Posts getPostsDao(){

        if (postsDao == null) {

            postsDao = new HibernatePostsDao(session);

        }

        return postsDao;
    }

}
