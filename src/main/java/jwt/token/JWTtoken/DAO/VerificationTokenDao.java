package jwt.token.JWTtoken.DAO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationTokenDao {

    private Long id;
    private String uuidToken;
    private UserDao userDao;
}
