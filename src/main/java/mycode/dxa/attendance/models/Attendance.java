package mycode.dxa.attendance.models;

import jakarta.persistence.*;
import lombok.*;
import mycode.dxa.classes.models.DanceClass;
import mycode.dxa.user.models.User;

import java.time.LocalDate;

@Entity
@Table(name = "attendances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    @ToString.Exclude
    private DanceClass danceClass;

    @Column(nullable = false)
    private LocalDate date;

    private boolean present;
}