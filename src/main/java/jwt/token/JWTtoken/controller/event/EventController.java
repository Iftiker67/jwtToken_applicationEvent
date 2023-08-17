package jwt.token.JWTtoken.controller.event;

import jakarta.servlet.http.HttpServletRequest;
import jwt.token.JWTtoken.DAO.UserDao;
import jwt.token.JWTtoken.models.PasswordModel;
import jwt.token.JWTtoken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserDao> createUser(@RequestBody UserDao userDao,HttpServletRequest request){
        UserDao createdUser = userService.createUserFromEventController(userDao, request);
        if(createdUser!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createdUser);
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<?> verifyRegistration(@RequestParam("token") String token){
        boolean verified = userService.validateVerificationToken(token);
        if(verified){
            return ResponseEntity.status(HttpStatus.OK).body("Verified");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Verified");
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody String username, HttpServletRequest request){
        boolean reset = userService.resetPassword(username,request);
        return reset? ResponseEntity.ok("Send")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not send");
    }

    @PostMapping("/resetPasswordConfirmation")
    public ResponseEntity<?> resetPasswordConfirmation
            (@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){
        boolean passwordReset = userService.validatePasswordResetToken(token,passwordModel);
        if(passwordReset){
            return ResponseEntity.status(HttpStatus.OK).body("Password Reset");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password Not Reset");
    }

}
