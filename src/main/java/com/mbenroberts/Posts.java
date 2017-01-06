package com.mbenroberts;

import java.util.List;

public interface Posts {
    List<Post> all();
    Long save(Post post);
}
