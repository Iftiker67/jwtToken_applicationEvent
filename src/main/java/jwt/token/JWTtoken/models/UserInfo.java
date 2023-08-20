package jwt.token.JWTtoken.models;

import jwt.token.JWTtoken.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@ToString
@Getter
public class UserInfo implements UserDetails {
    private String email;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public UserInfo() {
    }

    public UserInfo(User user){
        this.email=user.getEmail();
        this.password=user.getPassword();
//        this.authorities= user.getRoles().stream().map(role ->{
//            return new SimpleGrantedAuthority(role.toString());
//        }).collect(Collectors.toList());
       // Arrays.asList(user.getRoles().stream().toArray().)
        this.authorities = user.getRoles().stream().map(role -> {
                                return new SimpleGrantedAuthority(role.getName().toString());
                                }).collect(Collectors.toList());

        this.active= user.isValidUser();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
