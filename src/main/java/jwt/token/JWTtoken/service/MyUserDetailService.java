package jwt.token.JWTtoken.service;


import jwt.token.JWTtoken.entity.User;
import jwt.token.JWTtoken.models.UserInfo;
import jwt.token.JWTtoken.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userDetails = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("Not found"));
        UserInfo userInfo = new UserInfo(userDetails);
//        System.out.println(userInfo);
        return userInfo;
    }
}
