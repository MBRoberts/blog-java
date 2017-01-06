package com.mbenroberts;

public class DaoFactory {
    private static Posts postsDao;

    public static Posts getPostsDao(){

        if (postsDao == null) {

            postsDao = new ListPosts();

        }

        return postsDao;
    }

}
