package mycode.dxa.classes.service;

import mycode.dxa.classes.dtos.CreateDanceClassDto;
import mycode.dxa.classes.dtos.DanceClassResponse;
import mycode.dxa.classes.mappers.DanceClassMapper;
import mycode.dxa.classes.models.DanceClass;
import mycode.dxa.classes.repository.DanceClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassCommandServiceImpl implements ClassCommandService {

    private final DanceClassRepository classRepository;
    private final DanceClassMapper classMapper;

    public ClassCommandServiceImpl(DanceClassRepository classRepository, DanceClassMapper classMapper) {
        this.classRepository = classRepository;
        this.classMapper = classMapper;
    }

    @Override
    public DanceClassResponse createClass(CreateDanceClassDto dto) {
        if (classRepository.existsByTitle(dto.title())) {
            throw new RuntimeException("Un curs cu acest titlu există deja!");
        }
        DanceClass danceClass = classMapper.mapCreateDtoToEntity(dto);
        return classMapper.mapToResponse(classRepository.save(danceClass));
    }

    @Override
    public void deleteClass(Long id) {
        if (!classRepository.existsById(id)) {
            throw new RuntimeException("Cursul nu a fost găsit!");
        }
        classRepository.deleteById(id);
    }


}