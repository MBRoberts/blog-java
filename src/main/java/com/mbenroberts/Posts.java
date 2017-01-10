package com.mbenroberts;

import java.util.List;

public interface Posts {

    List<Post> all(int page);

    void save(Post post);

    Long getPostsCount();

    Post getPostById(Long id);

    void update(Post post);
}
