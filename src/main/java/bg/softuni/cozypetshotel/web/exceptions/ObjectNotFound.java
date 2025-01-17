package bg.softuni.cozypetshotel.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ObjectNotFound {
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public class ObjectNotFoundException extends RuntimeException {
        public ObjectNotFoundException(String message) {
            super(message);
        }
    }
}
