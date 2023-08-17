package jwt.token.JWTtoken.service;

import jakarta.servlet.http.HttpServletRequest;
import jwt.token.JWTtoken.DAO.UserDao;
import jwt.token.JWTtoken.DAO.VerificationTokenDao;
import jwt.token.JWTtoken.converter.UserDAOToUser;
import jwt.token.JWTtoken.converter.UserToUserDAO;
import jwt.token.JWTtoken.converter.event.VerificationTokenToVerificationTokenDao;
import jwt.token.JWTtoken.entity.User;
import jwt.token.JWTtoken.entity.event.ResetPasswordToken;
import jwt.token.JWTtoken.entity.event.VerificationToken;
import jwt.token.JWTtoken.evenCreation.event.CompleteRegestrationEvent;
import jwt.token.JWTtoken.evenCreation.event.ResetPasswordEvent;
import jwt.token.JWTtoken.models.PasswordModel;
import jwt.token.JWTtoken.repo.UserRepo;
import jwt.token.JWTtoken.repo.event.ResetPasswordTokenRepo;
import jwt.token.JWTtoken.repo.event.VerificationTokenRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    @Autowired
    private ResetPasswordTokenRepo resetPasswordTokenRepo;

    @Autowired
    private UserDAOToUser userDAOToUser;

    @Autowired
    private UserToUserDAO userToUserDAO;

    @Autowired
    private VerificationTokenToVerificationTokenDao vfToVfDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher publisher;


    public UserDao createUser(UserDao userDao) {
        User user = userDAOToUser.apply(userDao);
        if(user!=null){
            user.setPassword(passwordEncoder.encode(userDao.getPassword()));
            System.out.println(user.getPassword());
            User createdUser = userRepo.save(user);
            if(createdUser!=null){
                return userToUserDAO.apply(createdUser);
            }
        }
        throw new RuntimeException("Not saved");
    }

    public UserDao createUserFromEventController(UserDao userDao, HttpServletRequest request){
        User user = userDAOToUser.apply(userDao);
        if(user!=null){
            user.setPassword(passwordEncoder.encode(userDao.getPassword()));
            System.out.println(user.getPassword());
            User createdUser = userRepo.save(user);
            if(createdUser!=null){
                publisher.publishEvent(new CompleteRegestrationEvent(createdUser,appplicationUrl(request)));
                return userToUserDAO.apply(createdUser);
            }
        }
        throw new RuntimeException("Not saved");
    }

    private String appplicationUrl(HttpServletRequest request) {
        String applicationURL
                = "http://"
                +request.getServerName()
                +":"
                +request.getServerPort()
                +request.getContextPath();
        return applicationURL;
    }


    public VerificationTokenDao createVerificationToken(String verificationToken, User user) {
        VerificationToken createdVerficationToken = new VerificationToken(verificationToken,user);
        VerificationToken vf = verificationTokenRepo.save(createdVerficationToken);
        if(vf != null){
            return vfToVfDAO.apply(vf);
        }
        return null;
    }

    public Boolean validateVerificationToken(String token){
        VerificationToken verificationToken = verificationTokenRepo.findByUuidToken(token);
        boolean verified = false;
        if(verificationToken != null){
            if((Calendar.getInstance().getTime().getSeconds()
                    - verificationToken.getExprirationTime().getSeconds()) >0 ){
                verified = true;
                User user = verificationToken.getUser();
                user.setValidUser(true);
                userRepo.save(user);
                return verified;
            }
            else {
                String uuidToken = UUID.randomUUID().toString();
                verificationToken.setUuidToken(uuidToken);
                verificationTokenRepo.save(verificationToken);
            }
        }

        return verified;
    }

    public boolean resetPassword(String email, HttpServletRequest request) {
        System.out.println(email);
//        User user = userRepo.findByEmail(email).orElseThrow(()->{
//            throw new RuntimeException("Username Not found");
//        });
        Optional<User> optionalUser  = userRepo.findByEmail(email);
        System.out.println(optionalUser.get());
        if(optionalUser.isPresent()){
            publisher.publishEvent(new ResetPasswordEvent(optionalUser.get(),appplicationUrl(request)));
            return true;
        }
        return false;
    }

    public boolean validatePasswordResetToken(String resetToken, PasswordModel passwordModel) {
        Optional<ResetPasswordToken> optional=  resetPasswordTokenRepo.findByUuidResetToken(resetToken);
        if(optional.isPresent()){
            ResetPasswordToken resetPasswordToken = optional.get();

            if(passwordModel.getEmail().equals(resetPasswordToken.getUser().getEmail())){
                if( resetPasswordToken.getExprirationTime().getTime() -
                Calendar.getInstance().getTime().getTime() >0 ) {
                    User user = resetPasswordToken.getUser();
                    user.setPassword(passwordEncoder.encode(passwordModel.getNewPassword()));
                    userRepo.save(user);
                    return true;
                }
            }
        }
        return false;
    }
}
