package mycode.dxa.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mycode.dxa.user.dtos.CreateStudentDto;
import mycode.dxa.user.dtos.UpdateUserDto;
import mycode.dxa.user.dtos.UserListResponse;
import mycode.dxa.user.dtos.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Tag(name = "Users", description = "Operatii pentru gestionarea utilizatorilor")
public interface UserControllerApi {

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista tuturor utilizatorilor", description = "Returneaza lista de studenti filtrata dupa nume, status sau curs")
    UserListResponse getAllUsers(
            @Parameter(description = "Nume sau Prenume") @RequestParam(required = false) String search,
            @Parameter(description = "Status (Active/Inactive)") @RequestParam(required = false) String status,
            @Parameter(description = "ID-ul cursului") @RequestParam(required = false) Long courseId
    );

    // --- AICI AM FĂCUT CORECTURA: din "/student" în "/users/student" ---
    @PostMapping("/users/student")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Inregistrare student", description = "Creeaza un nou cont de tip student")
    UserResponse createStudent(@Valid @RequestBody CreateStudentDto createStudentDto);

    @PutMapping("/admin/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizare utilizator", description = "Permite adminului sa modifice datele unui student (inclusiv plati si status)")
    UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto);
}