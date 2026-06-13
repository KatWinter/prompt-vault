package me.winterbelle.prompvault.utils.helpers.auth;

import me.winterbelle.prompvault.utils.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

public class UserDetailsObject extends User {

    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Status status;

    public UserDetailsObject(
            Long userId,
            String username,
            String password,
            String firstName,
            String lastName,
            String email,
            Status status,
            Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Status getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return firstName + " " + lastName;
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
