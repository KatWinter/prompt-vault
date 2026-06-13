package me.winterbelle.prompvault.utils.helpers;

import jakarta.servlet.http.HttpSession;
import me.winterbelle.prompvault.models.data.PromptVaultUser;
import me.winterbelle.prompvault.repositories.PromptVaultUserRepository;
import me.winterbelle.prompvault.utils.enums.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionChecker {

    final private PromptVaultUserRepository promptVaultUserRepository;

    SessionChecker(PromptVaultUserRepository promptVaultUserRepository) {
        this.promptVaultUserRepository = promptVaultUserRepository;
    }

    public Boolean isCurrentUserAdmin(HttpSession session) {
        Long userId = getUserIdFromSession(session);
        if (userId == null) {
            return false;
        }

        Optional<PromptVaultUser> maybeUser = promptVaultUserRepository.findById(userId);
        return maybeUser.filter(promptVaultUser -> promptVaultUser.getRole() == Role.admin).isPresent();

    }

    public Boolean isCurrentUser(HttpSession session, Long userId) {
        return getUserIdFromSession(session).equals(userId);
    }

    private Long getUserIdFromSession(HttpSession session) {
        if (session == null) {
            return null;
        }
        Object userId = session.getAttribute("userId");
        if (userId instanceof Long) {
            return (Long) userId;
        }
        if (userId instanceof String) {
            return Long.parseLong((String) userId);
        }
        return null;
    }
}
