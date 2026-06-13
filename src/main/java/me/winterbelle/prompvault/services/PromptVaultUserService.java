package me.winterbelle.prompvault.services;

import me.winterbelle.prompvault.models.data.PromptVaultUser;
import me.winterbelle.prompvault.repositories.PromptVaultUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromptVaultUserService {

    final private PromptVaultUserRepository promptVaultUserRepository;

    public PromptVaultUserService(PromptVaultUserRepository promptVaultUserRepository) {
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
}
