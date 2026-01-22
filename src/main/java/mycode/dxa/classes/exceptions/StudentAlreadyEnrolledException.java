package mycode.dxa.classes.exceptions;

public class StudentAlreadyEnrolledException extends RuntimeException {
    public StudentAlreadyEnrolledException(String message) {
        super(message);
    }
}
