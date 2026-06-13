package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.PromptVaultUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromptVaultUserRepository extends JpaRepository<PromptVaultUser, Long> {

    Optional<PromptVaultUser> findByUsername(String username);

    Optional<PromptVaultUser> findByEmail(String email);

}
