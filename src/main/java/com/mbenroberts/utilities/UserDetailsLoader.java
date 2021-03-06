package com.mbenroberts.utilities;

import com.mbenroberts.interfaces.Roles;
import com.mbenroberts.interfaces.Users;
import com.mbenroberts.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customUserDetailsService")
public class UserDetailsLoader implements UserDetailsService {
    private final Users users;
    private final Roles roles;

    @Autowired
    public UserDetailsLoader(Users users, Roles roles) {
        this.users = users;
        this.roles = roles;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = users.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + email);
        }

        List<String> userRoles = roles.ofUserWith(user.getUsername());
        return new UserWithRoles(user, userRoles);
    }

}