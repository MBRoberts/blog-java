package com.mbenroberts;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    public boolean isLoggedIn() {
        boolean isAnonymousUser =
                SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
        return ! isAnonymousUser;
    }

    public User loggedInUser() {
        if (! isLoggedIn()) {
            return null;
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean postBelongsToUser(Post post){

        if (isLoggedIn()) {

            Long postUserId = post.getUser().getId();
            Long loggedInId = loggedInUser().getId();

            return postUserId == loggedInId;
        }

        return false;
    }

}
