import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NullExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(
            NullPointerException ex, WebRequest request) {

        String errorMessage = "Ошибка: попытка обращения к null-объекту. " +
                ex.getMessage();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        String errorMessage = "Ошибка: недопустимый аргумент. " +
                ex.getMessage();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {

        String errorMessage = "Ошибка: запрашиваемая сущность не найдена. " +
                ex.getMessage();

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}