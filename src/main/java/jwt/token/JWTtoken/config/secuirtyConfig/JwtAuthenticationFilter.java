package jwt.token.JWTtoken.config.secuirtyConfig;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


//        get token
        String requestToken = request.getHeader("Authorization");
//        System.out.println("Token "+requestToken);
        // Bearer token_number
        String userName = null;
        String token = null;

        if(requestToken != null  && requestToken.startsWith("Bearer ")) {
           try{
               token = requestToken.substring(7);
               //get user
               try {
                   userName = this.jwtUtil.extractUsername(token);
               } catch (IllegalArgumentException exception){
                   System.out.println("IllegalArgumentException exception");
               } catch (ExpiredJwtException exception){
                   System.out.println("ExpiredJwtException exception");
               } catch (MalformedJwtException exception){
                   System.out.println("Mallformed Exception");
               }
           } catch (StringIndexOutOfBoundsException exception){
               System.out.println("token is null");
           }

        }
//        else {
//            System.out.println("Token Not carried Bearer token");
//        }
        //once we get the token, now validate

        if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println("First entry security context");
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if(userDetails!=null){
                boolean validate = this.jwtUtil.validateToken(token,userDetails);

                if(validate){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } else {
                System.out.println("Username not found");
            }

        }

        filterChain.doFilter(request,response);
    }
}

/*
1) Get JWT token token from request
2) Validate token
3) Get USER from token
4) Load User associated with token
5) Set Spring security. Set Authentication
6) Forward Filter.
 */
