package antifraud.auth;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/user")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/auth/user/**")).hasAuthority(Authority.WRITE_USER.getAuthority())
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/auth/list/**")).hasAuthority(Authority.READ_USER.getAuthority())
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/antifraud/transaction/**")).hasAuthority(Authority.EXECUTE_TRANSACTION.getAuthority())
                        .requestMatchers(HttpMethod.PUT, "/api/auth/access/**", "/api/auth/role/**").hasAuthority(Authority.WRITE_USER.getAuthority())
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/actuator/shutdown")).permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll().requestMatchers("/error").permitAll()
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
