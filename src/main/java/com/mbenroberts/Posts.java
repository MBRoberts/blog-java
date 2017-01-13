package com.mbenroberts;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Posts extends CrudRepository<Post, Long> {
    public List<Post> findByUserId(Long UserId);

}
