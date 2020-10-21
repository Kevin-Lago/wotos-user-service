package com.wotos.wotosuserservice.model;

import com.wotos.wotosuserservice.model.LocalUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authrorities;

    public CustomUserDetails() {}

    public CustomUserDetails(LocalUser localUser) {
        this.username = localUser.getUsername();
        this.password = localUser.getPassword();
        this.active = true;
        this.authrorities = Arrays.stream(localUser.getRoles().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authrorities;
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
        return true; // change
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // change
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // change
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
