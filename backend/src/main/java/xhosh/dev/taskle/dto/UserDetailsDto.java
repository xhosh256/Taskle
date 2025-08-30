package xhosh.dev.taskle.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xhosh.dev.taskle.entity.User;

import java.util.Collection;
import java.util.List;


public class UserDetailsDto implements UserDetails {

    private final User INSTANCE;

    public UserDetailsDto(User user) {
        this.INSTANCE = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + INSTANCE.getRole().name()));
    }

    @Override
    public String getPassword() {
        return INSTANCE.getPassword();
    }

    @Override
    public String getUsername() {
        return INSTANCE.getUsername();
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
