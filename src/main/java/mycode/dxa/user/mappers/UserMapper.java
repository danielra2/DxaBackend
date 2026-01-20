package mycode.dxa.user.mappers;

import mycode.dxa.user.dtos.UserListResponse;
import mycode.dxa.user.dtos.UserResponse;
import mycode.dxa.user.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
public class UserMapper {

    // 1. Mapare de la Entitate la Response (Individual)
    public UserResponse mapToResponse(User user) {
        Objects.requireNonNull(user, "User entity is null");

        return new UserResponse(
                user.getId(),
                cleanText(user.getFirstName()),
                cleanText(user.getLastName()),
                cleanText(user.getEmail()),
                cleanText(user.getPhone()),
                calculateStudentStatus(user.getSubscriptionExpirationDate()), // Logica de business
                user.getSubscriptionExpirationDate(),
                user.getLastPaymentAmount()
        );
    }

    // 2. Mapare List<User> -> List<UserResponse> (folosind metoda generică mapList)
    public List<UserResponse> mapUserListToUserResponseList(List<User> list) {
        return mapList(list, this::mapToResponse);
    }

    public UserListResponse mapUserListToUserListResponse(List<User> list) {
        // Reutilizăm metoda de mai sus pentru lista internă
        return new UserListResponse(mapUserListToUserResponseList(list));
    }

    //CREATE STUDENT DTOOOO
    public User mapCreateStudentDtoToUser(mycode.dxa.user.dtos.CreateStudentDto dto) {
        Objects.requireNonNull(dto, "DTO-ul de creare este null");

        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password()); // Momentan text simplu, pe viitor se va cripta
        user.setPhone(dto.phone());
        user.setUserType(mycode.dxa.user.models.UserType.STUDENT);
        user.setSubscriptionExpirationDate(dto.subscriptionExpirationDate());

        return user;
    }


    // LOGICA DE BUSINESS (Specifică DXA)
    private String calculateStudentStatus(LocalDate expirationDate) {
        if (expirationDate == null) {
            return "NO_SUBSCRIPTION";
        }
        LocalDate today = LocalDate.now();
        if (!expirationDate.isBefore(today)) {
            return "Active";
        } else if (expirationDate.isAfter(today.minusMonths(1))) {
            return "Current";
        } else {
            return "Dormant";
        }
    }


    // METODE UTILITARE


    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    private static String nvl(String s) {
        return s == null ? "" : s;
    }

    private static String cleanText(String s) {
        return nvl(trim(s));
    }

    // Metoda generică care mapează orice listă, evitând null check-uri repetate
    private static <S, T> List<T> mapList(Collection<S> list, Function<S, T> mapper) {
        if (list == null || list.isEmpty()) {
            return List.of();
        }
        return list.stream()
                .filter(Objects::nonNull)
                .map(mapper)
                .toList();
    }
}