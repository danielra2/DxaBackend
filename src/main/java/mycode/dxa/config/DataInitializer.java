package mycode.dxa.config;

import mycode.dxa.user.models.User;
import mycode.dxa.user.models.UserType;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        String adminEmail = "admin@dxa.com";
        String adminPass = "admin123";

        // Caută dacă există adminul
        Optional<User> existingAdmin = userRepository.findByEmail(adminEmail);

        if (existingAdmin.isEmpty()) {
            // Cazul 1: Nu există, îl creăm de la zero
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setPhone("0000000000");
            admin.setPassword(passwordEncoder.encode(adminPass));
            admin.setUserType(UserType.ADMIN);
            userRepository.save(admin);
            System.out.println(">>> CONTUL DE ADMIN A FOST CREAT NOU <<<");
        } else {
            // Cazul 2: Există deja, dar ÎI RESCRIEM PAROLA ca să fim siguri
            User admin = existingAdmin.get();
            admin.setPassword(passwordEncoder.encode(adminPass));
            // Asigură-te că e ADMIN
            if (admin.getUserType() != UserType.ADMIN) {
                admin.setUserType(UserType.ADMIN);
            }
            userRepository.save(admin);
            System.out.println(">>> PAROLA DE ADMIN A FOST ACTUALIZATĂ/RESETATĂ <<<");
        }
    }
}