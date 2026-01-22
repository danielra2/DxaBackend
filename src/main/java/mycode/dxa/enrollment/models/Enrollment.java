package mycode.dxa.enrollment.models;

import jakarta.persistence.*;
import lombok.*;
import mycode.dxa.classes.models.DanceClass;
import mycode.dxa.user.models.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private DanceClass danceClass;

    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    // --- CÂMP NOU ---
    @Column(name = "participated")
    private boolean participated = false; // Implicit e false (nebifat)
}