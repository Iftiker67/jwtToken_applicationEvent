package jwt.token.JWTtoken.evenCreation.event;

import jwt.token.JWTtoken.entity.User;
import lombok.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.service.annotation.GetExchange;


@Getter
@Setter
public class CompleteRegestrationEvent extends ApplicationEvent {
    private String applicationURL;
    public CompleteRegestrationEvent(Object object,String url) {
        super(object);
        this.applicationURL=url;
    }
}
