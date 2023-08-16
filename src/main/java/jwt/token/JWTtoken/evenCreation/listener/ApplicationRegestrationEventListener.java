package jwt.token.JWTtoken.evenCreation.listener;

import jwt.token.JWTtoken.DAO.UserDao;
import jwt.token.JWTtoken.DAO.VerificationTokenDao;
import jwt.token.JWTtoken.entity.User;
import jwt.token.JWTtoken.evenCreation.event.CompleteRegestrationEvent;
import jwt.token.JWTtoken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.UUID;

@Component
@Slf4j
public class ApplicationRegestrationEventListener
        implements ApplicationListener<CompleteRegestrationEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(CompleteRegestrationEvent event) {
        User user = (User) event.getSource();
        String verificationToken = UUID.randomUUID().toString();

        VerificationTokenDao verificationTokenDao =
                userService.createVerificationToken(verificationToken,user);

        String url
                = event.getApplicationURL()
                +"/api/event/verifyRegistration?token="
                +verificationToken;

//        System.out.println(url);
        log.info("Generated URL to verify {}",url);

    }
}
