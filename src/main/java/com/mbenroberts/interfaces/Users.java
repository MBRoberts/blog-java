package com.mbenroberts.interfaces;

import com.mbenroberts.models.User;
import org.springframework.data.repository.CrudRepository;

public interface Users extends CrudRepository<User, Long> {
    public User findByUsername(String username);
}
