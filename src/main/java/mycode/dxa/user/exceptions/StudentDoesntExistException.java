package mycode.dxa.user.exceptions;

import mycode.dxa.user.constants.UserConstant;

public class StudentDoesntExistException extends RuntimeException {
    public StudentDoesntExistException() {
        super(UserConstant.STUDENT_DOESNT_EXIST);


    }
}
