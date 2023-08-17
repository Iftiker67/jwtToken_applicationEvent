package jwt.token.JWTtoken.evenCreation.listener;

import jwt.token.JWTtoken.entity.User;
import jwt.token.JWTtoken.entity.event.ResetPasswordToken;
import jwt.token.JWTtoken.evenCreation.event.ResetPasswordEvent;
import jwt.token.JWTtoken.repo.event.ResetPasswordTokenRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ResetPasswordEventListener implements ApplicationListener<ResetPasswordEvent> {

    @Autowired
    private ResetPasswordTokenRepo resetPasswordTokenRepo;

    @Override
    public void onApplicationEvent(ResetPasswordEvent event) {

        User user = (User)event.getSource();
        String uuidTokenForResetPassword = UUID.randomUUID().toString();

        ResetPasswordToken resetPasswordToken 
                = new ResetPasswordToken(uuidTokenForResetPassword,user);
        resetPasswordTokenRepo.save(resetPasswordToken);
        
//        event.getApplicationUrl();
        String url = generateUrl(uuidTokenForResetPassword, event);
        log.info("Reset Password Token : {}",url);
    }

    private String generateUrl(String uuidTokenForResetPassword, ResetPasswordEvent event) {
        return event.getApplicationUrl()
                +"/api/event/resetPasswordConfirmation?token="
                +uuidTokenForResetPassword;
    }
}
