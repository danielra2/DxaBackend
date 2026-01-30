package mycode.dxa.classes.controller;

import mycode.dxa.classes.dtos.CreateDanceClassDto;
import mycode.dxa.classes.dtos.DanceClassResponse;
import mycode.dxa.classes.service.ClassCommandService;
import mycode.dxa.classes.service.ClassQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ClassController implements ClassControllerApi {

    private final ClassCommandService commandService;
    private final ClassQueryService queryService;

    public ClassController(ClassCommandService commandService, ClassQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Override
    public ResponseEntity<List<DanceClassResponse>> getAllClasses() {
        return ResponseEntity.ok(queryService.getAllClasses());
    }

    @Override
    public ResponseEntity<DanceClassResponse> createClass(CreateDanceClassDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.createClass(dto));
    }

    @Override
    public ResponseEntity<Void> deleteClass(Long id) {
        commandService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

}