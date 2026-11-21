package com.example.Gestion.config;

import com.example.Gestion.entity.Usuario;
import com.example.Gestion.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                .requestMatchers("/incidencias/nueva", "/incidencias/guardar").hasAnyRole("ESTUDIANTE", "DOCENTE", "ADMINISTRADOR")
                .requestMatchers("/admin/**", "/usuarios/**", "/reportes/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/tecnico/**").hasRole("TECNICO")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepo) {
        return username -> {
            Usuario usuario = usuarioRepo.findByCorreo(username);
            if (usuario == null) {
                throw new org.springframework.security.core.userdetails.UsernameNotFoundException("Usuario no encontrado: " + username);
            }
            return User
                .withUsername(usuario.getCorreo())
                .password(usuario.getClaveHash())
                .roles(usuario.getRol().getNombre())
                .disabled(usuario.getEstado() == Usuario.EstadoUsuario.INACTIVO)
                .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
