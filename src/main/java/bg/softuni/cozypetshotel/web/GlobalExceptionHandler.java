package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.web.exceptions.ObjectNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFound.ObjectNotFoundException.class)
    public ModelAndView handleObjectNotFound(ObjectNotFound objectNotFound) {
        ModelAndView modelAndView = new ModelAndView("404");
        modelAndView.addObject("object", objectNotFound);

        return modelAndView;
    }
}
