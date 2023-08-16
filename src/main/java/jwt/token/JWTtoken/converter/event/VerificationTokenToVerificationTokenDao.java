package jwt.token.JWTtoken.converter.event;

import jwt.token.JWTtoken.DAO.UserDao;
import jwt.token.JWTtoken.DAO.VerificationTokenDao;
import jwt.token.JWTtoken.converter.UserToUserDAO;
import jwt.token.JWTtoken.entity.event.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class VerificationTokenToVerificationTokenDao
        implements Function<VerificationToken, VerificationTokenDao> {

    @Autowired
    private UserToUserDAO userToUserDAO;

    @Override
    public VerificationTokenDao apply(VerificationToken verificationToken) {
        UserDao userDao = userToUserDAO.apply(verificationToken.getUser());

        return VerificationTokenDao.builder()
                .id(verificationToken.getId())
                .uuidToken(verificationToken.getUuidToken())
                .userDao(userDao)
                .build();
    }
}
