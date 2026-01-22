package mycode.dxa.classes.service;

import mycode.dxa.classes.dtos.CreateDanceClassDto;
import mycode.dxa.classes.dtos.DanceClassResponse;
import java.util.List;

public interface ClassCommandService {
    DanceClassResponse createClass(CreateDanceClassDto dto);
    void deleteClass(Long id);

}