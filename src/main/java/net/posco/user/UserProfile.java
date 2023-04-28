package net.posco.user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import net.posco.util.SimpleGrantedAuthority;

public class UserProfile implements UserDetails {

    private User user;

    public UserProfile(User user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole();
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
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
        return user.isEnabled();
    }

    public Integer getId() {
        return this.user.getId();
    }

    public String getFullname() {
        return this.user.getLastName() + " " + this.user.getFirstName();
    }

    public String getFirstname() {
        return this.user.getFirstName();
    }

    public void setPicture(String picture) {
        this.user.setPicture(picture);
    }

    public String getPicture() {
        return ("/posco/upload/picture/user/profile-picture/" + this.user.getId() + "/" + this.user.getPicture());
    }
}
