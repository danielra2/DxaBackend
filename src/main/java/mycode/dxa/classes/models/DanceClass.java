package mycode.dxa.classes.models;

import jakarta.persistence.*;
import lombok.*;
import mycode.dxa.enrollment.models.Enrollment;
import mycode.dxa.user.models.User;

import java.util.List;

@Entity
@Table(name = "dance_classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanceClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String schedule;

    private String location;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "class_instructors",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    @ToString.Exclude
    private List<User> instructors;

    @OneToMany(mappedBy = "danceClass", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Enrollment> enrollments;
}