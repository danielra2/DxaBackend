package mycode.dxa.user.controller;

import jakarta.validation.Valid;
import mycode.dxa.security.JwtUtils;
import mycode.dxa.user.dtos.AuthResponse;
import mycode.dxa.user.dtos.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) { // Am pus <?> ca să putem returna și erori text

        try {
            // 1. Verificăm user și parola
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
            );

            // 2. Extragem detaliile userului care tocmai s-a logat corect
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 3. --- BLOCAJUL PENTRU NON-ADMINI ---
            // Verificăm dacă userul are rolul de ADMIN
            boolean isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                // Dacă NU e admin, refuzăm login-ul, chiar dacă parola e corectă
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Doar adminii se pot loga deocamdata."));
            }
            // --------------------------------------

            // 4. Dacă e Admin, continuăm logica normală (Setăm context, generăm token)
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtils.generateToken(userDetails);

            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_STUDENT");

            return ResponseEntity.ok(new AuthResponse(jwtToken, userDetails.getUsername(), role));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email sau parola incorecta"));
        }
    }
}