package jwt.token.JWTtoken.controller;

import jwt.token.JWTtoken.entity.Role;
import jwt.token.JWTtoken.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/role")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
       Role createdRole = roleService.createRole(role);

       return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }
}
