package mycode.dxa.user.models;

import jakarta.persistence.*;
import lombok.*;
import mycode.dxa.classes.models.DanceClass;
import mycode.dxa.enrollment.models.Enrollment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="user_type", nullable = false)
    private UserType userType;

    @Column(name="created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String phone;

    @Column(name = "subscription_expiration_date")
    private LocalDate subscriptionExpirationDate;

    @Column(name = "last_payment_amount")
    private Double lastPaymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "last_payment_method")
    private PaymentMethod lastPaymentMethod;

    @ManyToMany(mappedBy = "instructors", fetch = FetchType.LAZY)
    private List<DanceClass> teachingClasses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;
}