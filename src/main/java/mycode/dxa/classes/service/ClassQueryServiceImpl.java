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
    private ClassQueryService classQueryService;
    private DanceClassMapper danceClassMapper;
    private DanceClassRepository danceClassRepository;

    public ClassQueryServiceImpl(ClassQueryService classQueryService,DanceClassMapper danceClassMapper,DanceClassRepository danceClassRepository){
        this.classQueryService=classQueryService;
        this.danceClassMapper=danceClassMapper;
        this.danceClassRepository=danceClassRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DanceClassResponse> getAllClasses() {
        return danceClassMapper.mapListToResponse(danceClassRepository.findAll());
    }
}
