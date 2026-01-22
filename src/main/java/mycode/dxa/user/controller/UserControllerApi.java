package mycode.dxa.user.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista tuturor utilizatorilor", description = "Returneaza lista completa de studenti (momentan doar studenti conform implementarii)")
    UserListResponse getAllUsers();

    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Inregistrare student", description = "Creeaza un nou cont de tip student")
    UserResponse createStudent(@Valid @RequestBody CreateStudentDto createStudentDto);

    @PutMapping("/admin/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizare utilizator", description = "Permite adminului sa modifice datele unui student (inclusiv plati si status)")
    UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto);
}