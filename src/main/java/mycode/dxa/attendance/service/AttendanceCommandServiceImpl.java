package mycode.dxa.attendance.service;

import mycode.dxa.attendance.dtos.MarkAttendanceDto;
import mycode.dxa.attendance.models.Attendance;
import mycode.dxa.attendance.repository.AttendanceRepository;
import mycode.dxa.classes.models.DanceClass;
import mycode.dxa.classes.repository.DanceClassRepository;
import mycode.dxa.user.models.User;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AttendanceCommandServiceImpl implements AttendanceCommandService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final DanceClassRepository danceClassRepository;

    public AttendanceCommandServiceImpl(AttendanceRepository attendanceRepository,
                                        UserRepository userRepository,
                                        DanceClassRepository danceClassRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.danceClassRepository = danceClassRepository;
    }

    @Override
    public void markAttendance(MarkAttendanceDto dto) {
        Long studentId = dto.studentId();
        Long classId = dto.classId();
        var date = dto.date();
        boolean isPresent = dto.present();
        Optional<Attendance> existing = attendanceRepository.findByStudentIdAndDanceClassIdAndDate(studentId, classId, date);
        if (existing.isPresent()) {
            Attendance attendance = existing.get();
            attendance.setPresent(isPresent);
            attendanceRepository.save(attendance);
        } else {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            DanceClass danceClass = danceClassRepository.findById(classId)
                    .orElseThrow(() -> new RuntimeException("Class not found"));

            Attendance newAttendance = new Attendance();
            newAttendance.setStudent(student);
            newAttendance.setDanceClass(danceClass);
            newAttendance.setDate(date);
            newAttendance.setPresent(isPresent);

            attendanceRepository.save(newAttendance);
        }
    }
}