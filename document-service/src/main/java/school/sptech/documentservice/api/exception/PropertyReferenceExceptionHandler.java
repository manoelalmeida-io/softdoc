package school.sptech.documentservice.api.exception;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PropertyReferenceExceptionHandler {

  @ExceptionHandler(PropertyReferenceException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void handle(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.BAD_REQUEST.value());
  }
}
