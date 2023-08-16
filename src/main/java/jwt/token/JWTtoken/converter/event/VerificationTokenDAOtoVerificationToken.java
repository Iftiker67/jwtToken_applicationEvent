package jwt.token.JWTtoken.converter.event;

import jwt.token.JWTtoken.DAO.VerificationTokenDao;
import jwt.token.JWTtoken.converter.UserDAOToUser;
import jwt.token.JWTtoken.entity.User;
import jwt.token.JWTtoken.entity.event.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class VerificationTokenDAOtoVerificationToken
        implements Function<VerificationTokenDao, VerificationToken> {

    @Autowired
    private UserDAOToUser userDAOToUser;

    @Override
    public VerificationToken apply(VerificationTokenDao verificationTokenDao) {

        User user = userDAOToUser.apply(verificationTokenDao.getUserDao());

        return VerificationToken.builder()
                .uuidToken(verificationTokenDao.getUuidToken())
                .user(user)
                .build();
    }
}
