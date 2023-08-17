package jwt.token.JWTtoken.entity.event;

import jakarta.persistence.*;
import jwt.token.JWTtoken.entity.User;
import jwt.token.JWTtoken.staticValue.ApplicationStaticValue;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String uuidResetToken;
    private Date exprirationTime;

    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "userId")
    private User user;

    public ResetPasswordToken(String uuidResetToken, User user){
        this.uuidResetToken = uuidResetToken;
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
