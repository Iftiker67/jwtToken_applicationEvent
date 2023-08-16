package jwt.token.JWTtoken.repo.event;

import jwt.token.JWTtoken.entity.event.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByUuidToken(String token);
}
