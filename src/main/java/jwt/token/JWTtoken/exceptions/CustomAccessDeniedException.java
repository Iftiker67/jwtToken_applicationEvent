package jwt.token.JWTtoken.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class CustomAccessDeniedException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public CustomAccessDeniedException(HttpStatus status, String message){
        System.out.println("Custome access exception");
        this.status= status;
        this.message = message;
    }

}
