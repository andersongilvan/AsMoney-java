package AsMoney.config.security;


import AsMoney.modules.user.exceptions.UserAlreadyExistsException;
import AsMoney.modules.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<MethodArgumentNotValidExceptionDto> errorsList = ex.getFieldErrors()
                .stream().map(e -> {
                    return new MethodArgumentNotValidExceptionDto(e.getField(), e.getDefaultMessage());
                }).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsList);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> userAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDto(ex.getMessage()));
    }

}

record ExceptionResponseDto(String message) { }


record MethodArgumentNotValidExceptionDto(String field, String message) {
}