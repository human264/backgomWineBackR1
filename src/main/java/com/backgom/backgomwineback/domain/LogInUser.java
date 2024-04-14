package com.backgom.backgomwineback.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


public class LogInUser implements UserDetails {

    private UUID id;

    private String email;

    private String password;

    private String phoneNumber;

    private String role;

    private String authProvider;


    public void setLogInInfo(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.phoneNumber = userEntity.getPhoneNumber();
        this.role = userEntity.getRole();
        this.authProvider = userEntity.getAuthProvider();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
