package jwt.token.JWTtoken.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwt.token.JWTtoken.exceptions.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.net.http.HttpClient;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandlerListener {

    @ExceptionHandler(UserNameNotFound.class)
    public ResponseEntity<ExceptionResponse>
    userNameNotFound(UserNameNotFound exception,WebRequest request){
        String getErrorMessage = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(HttpStatus.NOT_FOUND,getErrorMessage));
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> accessDenied(CustomAccessDeniedException customAccessDeniedException,
                                                          WebRequest request){

        log.info("ExcptionHAndler CustomAccessDeniedException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(customAccessDeniedException.getStatus(),
                        customAccessDeniedException.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessdeniedException(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   AccessDeniedException accessDeniedException){
        log.info("ExcptionHAndler AccessDeniedException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(HttpStatus.UNAUTHORIZED,
                        "USer is Unauthrorized"));
    }
}
