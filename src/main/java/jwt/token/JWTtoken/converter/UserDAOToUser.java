package jwt.token.JWTtoken.converter;

import jwt.token.JWTtoken.DAO.UserDao;
import jwt.token.JWTtoken.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDAOToUser implements Function<UserDao, User> {

    @Override
    public User apply(UserDao userDao) {
        return User.builder()
                .userId(userDao.getUserID())
                .email(userDao.getEmail())
                .password(userDao.getPassword())
                .build();
    }
}
