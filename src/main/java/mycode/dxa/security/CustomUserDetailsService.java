package mycode.dxa.security;

import mycode.dxa.user.models.User;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Căutăm userul în baza noastră de date folosind repository-ul existent
        User myUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 2. Transformăm rolul din ENUM în formatul cerut de Spring (ROLE_...)
        // Spring Security are nevoie de prefixul "ROLE_" pentru ca funcțiile precum .hasRole("ADMIN") să meargă.
        String roleName = "ROLE_" + myUser.getUserType().name();

        // 3. Returnăm obiectul User al Spring Security (nu entitatea noastră)
        return new org.springframework.security.core.userdetails.User(
                myUser.getEmail(),      // Username-ul (la noi e email)
                myUser.getPassword(),   // Parola (deja criptată în DB)
                Collections.singletonList(new SimpleGrantedAuthority(roleName)) // Lista de permisiuni
        );
    }
}