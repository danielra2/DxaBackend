package mycode.dxa.user.exceptions;

import mycode.dxa.user.constants.UserConstant;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super(UserConstant.EMAIL_ALREAADY_IN_USE);
    }
}
