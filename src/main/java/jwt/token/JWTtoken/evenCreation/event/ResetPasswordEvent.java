package jwt.token.JWTtoken.evenCreation.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResetPasswordEvent extends ApplicationEvent {
    private String applicationUrl;

    public ResetPasswordEvent(Object source, String url) {
        super(source);
        this.applicationUrl=url;
    }
}
