package jwt.token.JWTtoken.config.request;

import jwt.token.JWTtoken.entity.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtAuthRequest {

//    private User user;
//    private String jwt;
    private String userName;
    private String password;

}
