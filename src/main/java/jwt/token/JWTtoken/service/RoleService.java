package jwt.token.JWTtoken.service;

import jwt.token.JWTtoken.entity.Role;
import jwt.token.JWTtoken.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;
    public Role createRole(Role role) {
        return roleRepo.save(role);
    }
}
