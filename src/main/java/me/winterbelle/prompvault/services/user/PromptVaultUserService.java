package me.winterbelle.prompvault.services.user;

import me.winterbelle.prompvault.models.data.PromptVaultUser;
import me.winterbelle.prompvault.utils.enums.Status;

import java.util.List;
import java.util.Optional;

public interface PromptVaultUserService {

    void saveUser(PromptVaultUser user);

    Optional<PromptVaultUser> findUser(String username);

    Optional<PromptVaultUser> findUserByEmail(String email);

    Optional<PromptVaultUser> findUser(Long id);

    List<PromptVaultUser> findAllUsers();

    void updateUserStatus(Long id, Status status);
}
