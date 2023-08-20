package jwt.token.JWTtoken.exceptions;

import jwt.token.JWTtoken.exceptions.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.net.http.HttpClient;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlerListener {

    @ExceptionHandler(UserNameNotFound.class)
    public ResponseEntity<ExceptionResponse>
    userNameNotFound(UserNameNotFound exception,WebRequest request){
        String getErrorMessage = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(HttpStatus.NOT_FOUND,getErrorMessage));
    }
}
