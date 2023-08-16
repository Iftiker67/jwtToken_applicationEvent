package jwt.token.JWTtoken.repo;

import jwt.token.JWTtoken.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
