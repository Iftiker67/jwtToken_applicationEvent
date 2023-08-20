package jwt.token.JWTtoken.repo;

import jwt.token.JWTtoken.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

//    Optional<User> findValidUserByEmail(String email);


    Optional<User> findByValidUserTrueAndEmail(String email);
}
