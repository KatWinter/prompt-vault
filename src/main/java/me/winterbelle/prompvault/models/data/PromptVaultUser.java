package me.winterbelle.prompvault.models.data;

import jakarta.persistence.*;
import me.winterbelle.prompvault.utils.enums.Role;
import me.winterbelle.prompvault.utils.enums.Status;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prompt_vault_users",
        indexes = {
            @Index(columnList = "username"),
            @Index(columnList = "email")
        },
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
        }
)
public class PromptVaultUser extends AuditableEntity{

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    private String passwordHash;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Prompt> promptList = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Prompt> getPromptList() {
        return promptList;
    }

    public void setPromptList(List<Prompt> promptList) {
        this.promptList = promptList;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
