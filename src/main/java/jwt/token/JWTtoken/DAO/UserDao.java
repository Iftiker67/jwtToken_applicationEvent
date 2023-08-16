package jwt.token.JWTtoken.DAO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDao {

    private Long userID;
    private String email;
    private String password;
}
