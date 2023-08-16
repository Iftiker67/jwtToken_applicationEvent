package jwt.token.JWTtoken.converter;

import jwt.token.JWTtoken.DAO.UserDao;
import jwt.token.JWTtoken.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserToUserDAO implements Function<User, UserDao> {
    @Override
    public UserDao apply(User user) {
        return  UserDao.builder()
                .userID(user.getUserId())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
