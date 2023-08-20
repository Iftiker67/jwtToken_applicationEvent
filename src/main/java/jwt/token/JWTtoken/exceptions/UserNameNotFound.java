package jwt.token.JWTtoken.exceptions;

import jwt.token.JWTtoken.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameNotFound extends RuntimeException{
    private String message;

    public UserNameNotFound(){

    }

    public UserNameNotFound(String message){
        this.message = message;
    }
}
