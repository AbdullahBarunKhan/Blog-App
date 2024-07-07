package com.projects.blogapp.security;

import com.projects.blogapp.users.UsersEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JWTAuthentication implements Authentication {

    String jwt;

    public JWTAuthentication(String jwt) {
        this.jwt = jwt;
    }

    UsersEntity usersEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Returns the credentials of the Authentication request
     * eg.password or bearer token, or the cookie
     * @return
     */
    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * entity being authenticated
     * in this case it is user.
     * @return
     */
    @Override
    public Object getPrincipal() {
        return usersEntity;
    }

    @Override
    public boolean isAuthenticated() {
        return (usersEntity != null);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return "";
    }
}
