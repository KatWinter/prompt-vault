package me.winterbelle.prompvault.utils.helpers.auth;

import me.winterbelle.prompvault.utils.enums.Status;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

public class UserDetailsObject extends User {

    private final Long userId;
    private final Status status;

    public UserDetailsObject(Long userId, String username, @Nullable String password, Status status, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public Boolean isAdmin() {
        return this.getAuthorities().stream().anyMatch(a -> Objects.requireNonNull(a.getAuthority()).equals("ROLE_ADMIN"));
    }

    public Boolean isNotAdmin() {
        return !isAdmin();
    }

    @Override
    public boolean isEnabled() {
        return this.status == Status.enabled;
    }
}
