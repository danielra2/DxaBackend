package mycode.dxa.classes.mappers;

import mycode.dxa.classes.dtos.CreateDanceClassDto;
import mycode.dxa.classes.dtos.DanceClassResponse;
import mycode.dxa.classes.models.DanceClass;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DanceClassMapper {

    public DanceClassResponse mapToResponse(DanceClass danceClass) {
        return new DanceClassResponse(
                danceClass.getId(),
                danceClass.getTitle(),
                danceClass.getDescription(),
                danceClass.getSchedule(),
                danceClass.getLocation()
        );
    }

    public List<DanceClassResponse> mapListToResponse(List<DanceClass> classes) {
        return classes.stream().map(this::mapToResponse).toList();
    }

    public DanceClass mapCreateDtoToEntity(CreateDanceClassDto dto) {
        DanceClass danceClass = new DanceClass();
        danceClass.setTitle(dto.title());
        danceClass.setDescription(dto.description());
        danceClass.setSchedule(dto.schedule());
        danceClass.setLocation(dto.location());
        return danceClass;
    }
}