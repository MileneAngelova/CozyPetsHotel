package bg.softuni.cozypetshotel.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(code = HttpStatus.NOT_FOUND)
    public class ObjectNotFoundException  {
        public ObjectNotFoundException() {
            super();
        }
    }
