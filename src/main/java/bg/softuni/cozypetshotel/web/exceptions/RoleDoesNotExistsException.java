package bg.softuni.cozypetshotel.web.exceptions;

public class RoleDoesNotExistsException extends RuntimeException{
    public RoleDoesNotExistsException(String message) {
        super(message);
    }
}
