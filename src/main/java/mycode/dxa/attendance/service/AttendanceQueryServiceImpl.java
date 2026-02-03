package mycode.dxa.attendance.service;

import mycode.dxa.attendance.dtos.AttendanceRecordDto;
import mycode.dxa.attendance.dtos.StudentStatsDto;
import mycode.dxa.attendance.models.Attendance;
import mycode.dxa.attendance.repository.AttendanceRepository;
import mycode.dxa.classes.dtos.ClassStudentDto;
import mycode.dxa.classes.dtos.DanceClassResponse;
import mycode.dxa.classes.models.DanceClass;
import mycode.dxa.classes.repository.DanceClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AttendanceQueryServiceImpl implements AttendanceQueryService {

    private final AttendanceRepository attendanceRepository;
    private final DanceClassRepository danceClassRepository;

    public AttendanceQueryServiceImpl(AttendanceRepository attendanceRepository, DanceClassRepository danceClassRepository) {
        this.attendanceRepository = attendanceRepository;
        this.danceClassRepository = danceClassRepository;
    }

    @Override
    public boolean isStudentPresent(Long studentId, Long classId, LocalDate date) {
        return attendanceRepository.findByStudentIdAndDanceClassIdAndDate(studentId, classId, date)
                .map(Attendance::isPresent)
                .orElse(false);
    }

    @Override
    public DanceClassResponse getClassDetailsWithAttendance(Long classId, LocalDate date) {
        DanceClass danceClass = danceClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Cursul nu exista"));

        List<ClassStudentDto> studentDtos = danceClass.getEnrollments().stream()
                .filter(enrollment -> {
                    // FILTRARE: Studentul apare doar daca data cursului (date) este inaintea expirarii abonamentului specific
                    return enrollment.getExpirationDate() != null &&
                            (enrollment.getExpirationDate().isAfter(date) || enrollment.getExpirationDate().isEqual(date));
                })
                .map(enrollment -> {
                    var s = enrollment.getStudent();
                    boolean isPresent = isStudentPresent(s.getId(), classId, date);
                    return new ClassStudentDto(
                            s.getId(),
                            s.getFirstName() + " " + s.getLastName(),
                            isPresent,
                            enrollment.getExpirationDate() // Trimitem data catre frontend
                    );
                })
                .toList();

        return new DanceClassResponse(
                danceClass.getId(),
                danceClass.getTitle(),
                danceClass.getDescription(),
                danceClass.getSchedule(),
                danceClass.getLocation(),
                studentDtos
        );
    }

    @Override
    public StudentStatsDto getStudentStats(Long studentId, String range) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = "WEEK".equalsIgnoreCase(range) ? endDate.minusDays(7) : endDate.minusDays(30);

        List<Attendance> records = attendanceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate);
        int totalClasses = records.size();
        int attendedClasses = (int) records.stream().filter(Attendance::isPresent).count();
        double rate = totalClasses == 0 ? 0.0 : ((double) attendedClasses / totalClasses) * 100;

        List<AttendanceRecordDto> history = records.stream()
                .sorted(Comparator.comparing(Attendance::getDate).reversed())
                .map(a -> new AttendanceRecordDto(a.getDanceClass().getTitle(), a.getDate(), a.isPresent()))
                .toList();
        return new StudentStatsDto(rate, totalClasses, attendedClasses, history);
    }
}