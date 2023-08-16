package jwt.token.JWTtoken.controller;

import jwt.token.JWTtoken.DAO.UserDao;
import jwt.token.JWTtoken.config.request.JwtAuthRequest;
import jwt.token.JWTtoken.config.response.JwtAuthResponse;
import jwt.token.JWTtoken.config.secuirtyConfig.JwtUtil;
import jwt.token.JWTtoken.entity.User;
import jwt.token.JWTtoken.models.UserInfo;
import jwt.token.JWTtoken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @GetMapping("/admin")
    public String home(){
        return "Welcome Admin";
    }

    @GetMapping("user")
    public String user(Principal principal){
        return "Welcome User "+principal;
    }
    @GetMapping("/secure")
    public String secure(Principal principal){
        return "Welcome secure "+principal.getName();
    }

    @PostMapping("/user")
    public ResponseEntity<UserDao> createUser(@RequestBody UserDao userDao){
        UserDao createdUser = userService.createUser(userDao);
        if(createdUser!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
         }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createdUser);
    }
    @GetMapping("/authenticate")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody JwtAuthRequest jwtAuthRequest){
        System.out.println("Authentication");
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUserName(),jwtAuthRequest.getPassword()));
        } catch (Exception ex){
            System.out.println("Execption handler in controller level");
            System.out.println(ex);

        }
        UserInfo userDetails = (UserInfo) userDetailsService.loadUserByUsername(jwtAuthRequest.getUserName());
        System.out.println("Home Controller "+userDetails);
        String generatedToken = this.jwtUtil.generateToken(userDetails);
        System.out.println("token: "+generatedToken);
        return ResponseEntity.status(HttpStatus.OK).body(new JwtAuthResponse(generatedToken));
    }

}
