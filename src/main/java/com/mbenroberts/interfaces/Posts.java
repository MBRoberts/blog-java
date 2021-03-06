package com.mbenroberts.interfaces;

import com.mbenroberts.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Posts extends CrudRepository<Post, Long> {
    List<Post> findByUserId(Long UserId);

}
