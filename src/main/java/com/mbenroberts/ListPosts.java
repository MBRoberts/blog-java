package com.mbenroberts;

import java.util.ArrayList;
import java.util.List;

public class ListPosts implements Posts {

    private List<Post> posts;

    public ListPosts(){
        posts = new ArrayList<>();
    }

    @Override
    public List<Post> all() {
        return posts;
    }

    @Override
    public Long save(Post post) {

        post.setId((long) (posts.size() + 1));
        posts.add(post);

        return null;
    }
}
