package com.sitesstorageproject.security;

import com.sitesstorageproject.entities.Authority;
import com.sitesstorageproject.entities.Role;
import com.sitesstorageproject.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class UserCredits implements UserDetails {
    private final String username;

    private final String password;

    private final List<GrantedAuthority> rolesAndAuthorities;

    public UserCredits(User user) {
        username = user.getEmail();
        password = user.getPassword();
        rolesAndAuthorities = new LinkedList<>();
        for (Role role : user.getRoles()) {
            rolesAndAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        for (Authority authority : user.getAuthorities()) {
            rolesAndAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
