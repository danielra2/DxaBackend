package mycode.dxa.config;

import mycode.dxa.user.models.User;
import mycode.dxa.user.models.UserType;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificăm dacă există deja adminul ca să nu-l creăm de două ori
        if (!userRepository.existsByEmail("admin@dxa.com")) {
            User admin = new User();
            admin.setEmail("admin@dxa.com");
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setPhone("0000000000");
            // Setăm parola criptată
            admin.setPassword(passwordEncoder.encode("admin123"));
            // Setăm tipul ADMIN
            admin.setUserType(UserType.ADMIN);

            userRepository.save(admin);
            System.out.println(">>> CONTUL DE ADMIN A FOST CREAT: admin@dxa.com / admin123 <<<");
        }
    }
}