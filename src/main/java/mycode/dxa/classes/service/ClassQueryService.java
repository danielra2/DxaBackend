package mycode.dxa.classes.service;

import mycode.dxa.classes.dtos.DanceClassResponse;

import java.util.List;

public interface ClassQueryService {
    List<DanceClassResponse> getAllClasses();
}
