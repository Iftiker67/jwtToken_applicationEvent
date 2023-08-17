package jwt.token.JWTtoken.repo.event;

import jwt.token.JWTtoken.entity.event.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordTokenRepo extends JpaRepository<ResetPasswordToken,Long> {
    Optional<ResetPasswordToken> findByUuidResetToken(String resetToken);
}
