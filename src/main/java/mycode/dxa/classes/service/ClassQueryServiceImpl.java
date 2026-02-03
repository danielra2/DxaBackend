package mycode.dxa.classes.service;

import mycode.dxa.classes.dtos.DanceClassResponse;
import mycode.dxa.classes.mappers.DanceClassMapper;
import mycode.dxa.classes.repository.DanceClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)

public class ClassQueryServiceImpl implements ClassQueryService {
    private final DanceClassMapper danceClassMapper;
    private final DanceClassRepository danceClassRepository;

    public ClassQueryServiceImpl(DanceClassMapper danceClassMapper,DanceClassRepository danceClassRepository){
        this.danceClassMapper=danceClassMapper;
        this.danceClassRepository=danceClassRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DanceClassResponse> getAllClasses() {
        return danceClassMapper.mapListToResponse(danceClassRepository.findAll());
    }
}
