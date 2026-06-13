package me.winterbelle.prompvault.utils.helpers.configs;

import me.winterbelle.prompvault.utils.helpers.auth.PromptVaultUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpForm -> {
                    httpForm.loginPage("/req/login").permitAll();
                    httpForm.loginProcessingUrl("/req/login");
                    httpForm.defaultSuccessUrl("/home", true);
                })
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/req/**", "/css/**", "/js/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PromptVaultUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
