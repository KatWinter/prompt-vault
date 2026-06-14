package me.winterbelle.prompvault.utils.helpers.auth;

import me.winterbelle.prompvault.models.data.PromptVaultUser;
import me.winterbelle.prompvault.services.user.PromptVaultUserService;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromptVaultUserDetailsService implements UserDetailsService {
    final private PromptVaultUserService promptVaultUserService;

    PromptVaultUserDetailsService(PromptVaultUserService promptVaultUserService) {
        this.promptVaultUserService = promptVaultUserService;
    }

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        final Optional<PromptVaultUser> maybeUser = promptVaultUserService.findUser(username);
        if (maybeUser.isPresent()) {
            final PromptVaultUser user = maybeUser.get();
            return new UserDetailsObject(user.getId(),
                    user.getUsername(),
                    user.getPasswordHash(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getStatus(),
                    List.of(
                            new SimpleGrantedAuthority("ROLE_" + user.getRole().name().toUpperCase()
                            )
                    )
            );
        }
        throw UsernameNotFoundException.fromUsername(username);
    }
}
