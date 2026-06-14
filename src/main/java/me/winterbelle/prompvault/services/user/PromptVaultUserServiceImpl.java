package me.winterbelle.prompvault.services.user;

import me.winterbelle.prompvault.models.data.PromptVaultUser;
import me.winterbelle.prompvault.repositories.PromptVaultUserRepository;
import me.winterbelle.prompvault.utils.enums.Status;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromptVaultUserServiceImpl implements PromptVaultUserService {

    final private PromptVaultUserRepository promptVaultUserRepository;

    public PromptVaultUserServiceImpl(PromptVaultUserRepository promptVaultUserRepository) {
        this.promptVaultUserRepository = promptVaultUserRepository;
    }

    public void saveUser(PromptVaultUser user) {
        promptVaultUserRepository.save(user);
    }

    public Optional<PromptVaultUser> findUser(String username) {
        return promptVaultUserRepository.findByUsername(username);
    }

    public Optional<PromptVaultUser> findUserByEmail(String email) {
        return promptVaultUserRepository.findByEmail(email);
    }

    public Optional<PromptVaultUser> findUser(Long id) {
        return promptVaultUserRepository.findById(id);
    }

    @Override
    public List<PromptVaultUser> findAllUsers() {
        return promptVaultUserRepository.findAll();
    }

    @Override
    public void updateUserStatus(Long id, Status status) {
        PromptVaultUser user = promptVaultUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setStatus(status);
        promptVaultUserRepository.save(user);
    }
}
