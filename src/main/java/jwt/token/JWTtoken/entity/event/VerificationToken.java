package jwt.token.JWTtoken.entity.event;

import jakarta.persistence.*;
import jwt.token.JWTtoken.entity.User;
import jwt.token.JWTtoken.staticValue.ApplicationStaticValue;
import lombok.*;

import java.util.Calendar;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuidToken;

    private Date exprirationTime;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    public VerificationToken(String uuidToken, User user) {
        this.uuidToken = uuidToken;
        this.user = user;
        this.exprirationTime = setExpirationTime(ApplicationStaticValue.EXPIRATION_TIME);
    }

    private Date setExpirationTime(int expiration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expiration);
        return new Date(calendar.getTime().getTime());
    }
}
