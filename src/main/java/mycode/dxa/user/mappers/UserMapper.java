package mycode.dxa.user.mappers;

import mycode.dxa.user.dtos.CreateStudentDto;
import mycode.dxa.user.dtos.EnrolledClassDto;
import mycode.dxa.user.dtos.UserListResponse;
import mycode.dxa.user.dtos.UserResponse;
import mycode.dxa.enrollment.models.Enrollment;
import mycode.dxa.user.models.User;
import mycode.dxa.user.models.UserType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
public class UserMapper {

    public UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                // INVERSEAZĂ AICI DACĂ E CAZUL:
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getStatus(),
                user.getSubscriptionExpirationDate(),
                user.getLastPaymentAmount(),
                user.getNextPaymentAmount(),
                mapEnrollmentsToDto(user.getEnrollments())
        );
    }


    // ... (celelalte metode de listare rămân neschimbate) ...
    public List<UserResponse> mapUserListToUserResponseList(List<User> list) {
        return mapList(list, this::mapToResponse);
    }

    public UserListResponse mapUserListToUserListResponse(List<User> list) {
        return new UserListResponse(mapUserListToUserResponseList(list));
    }

    public User mapCreateStudentDtoToUser(CreateStudentDto dto) {
        Objects.requireNonNull(dto, "DTO-ul de creare este null");

        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setPhone(dto.phone());
        user.setUserType(UserType.STUDENT);
        user.setSubscriptionExpirationDate(dto.subscriptionExpirationDate());

        // MAPARE LA CREARE
        user.setLastPaymentAmount(dto.lastPaymentAmount() != null ? dto.lastPaymentAmount() : 0.0);
        user.setNextPaymentAmount(dto.nextPaymentAmount());

        return user;
    }
    // Modifică metoda mapEnrollmentsToDto pentru a include noul câmp expirationDate
    private List<EnrolledClassDto> mapEnrollmentsToDto(List<Enrollment> enrollments) {
        if (enrollments == null || enrollments.isEmpty()) {
            return List.of();
        }
        return enrollments.stream()
                .map(enrollment -> {
                    var danceClass = enrollment.getDanceClass();
                    return new EnrolledClassDto(
                            danceClass.getId(),
                            danceClass.getTitle(),
                            danceClass.getSchedule(),
                            enrollment.getExpirationDate() // <--- ASIGURĂ-TE CĂ ACEASTĂ LINIE EXISTĂ
                    );
                })
                .toList();
    }
    // ... (restul metodelor rămân la fel: updateUserFromDto, mapEnrollmentsToDto, etc.) ...

    // Asigură-te că păstrezi metodele updateUserFromDto, mapEnrollmentsToDto, calculateStudentStatus și cele utilitare!
    // Le poți copia din fișierul vechi dacă nu le-am scris aici explicit pe toate.

    public void updateUserFromDto(mycode.dxa.user.dtos.UpdateUserDto dto, User user) {
        if (dto.firstName() != null) user.setFirstName(dto.firstName());
        if (dto.lastName() != null) user.setLastName(dto.lastName());
        if (dto.phone() != null) user.setPhone(dto.phone());
        if (dto.subscriptionExpirationDate() != null) {
            user.setSubscriptionExpirationDate(dto.subscriptionExpirationDate());
        }
        if (dto.lastPaymentAmount() != null) user.setLastPaymentAmount(dto.lastPaymentAmount());
        if (dto.nextPaymentAmount() != null) user.setNextPaymentAmount(dto.nextPaymentAmount());
    }

    EnrolledClassDto toEnrolledClassDto(Enrollment enrollment) {
        if (enrollment == null || enrollment.getDanceClass() == null) return null;
        return new EnrolledClassDto(
                enrollment.getDanceClass().getId(),
                enrollment.getDanceClass().getTitle(),
                enrollment.getDanceClass().getSchedule(),
                enrollment.getExpirationDate() // MAPARE NOUĂ
        );
    }

    private String calculateStudentStatus(LocalDate expirationDate) {
        if (expirationDate == null) return "NO_SUBSCRIPTION";
        LocalDate today = LocalDate.now();
        if (!expirationDate.isBefore(today)) return "Active";
        else if (expirationDate.isAfter(today.minusMonths(1))) return "Current";
        else return "Dormant";
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static String nvl(String s) { return s == null ? "" : s; }
    private static String cleanText(String s) { return nvl(trim(s)); }
    private static <S, T> List<T> mapList(Collection<S> list, Function<S, T> mapper) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().filter(Objects::nonNull).map(mapper).toList();
    }
}