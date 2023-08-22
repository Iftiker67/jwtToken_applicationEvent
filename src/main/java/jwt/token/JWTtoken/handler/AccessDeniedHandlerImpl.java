package jwt.token.JWTtoken.handler;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwt.token.JWTtoken.exceptions.CustomAccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.time.LocalDateTime;


@Component
@Slf4j
@ResponseBody
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        log.info("AccessDeniedHandlerImpl implemets AccesDeniedHandler");

        //not working throwing custome exception and using controller advice for it not work
        //throw new CustomAccessDeniedException(HttpStatus.BAD_REQUEST,"Access Denied");


        ResponseEntity<?> responseEntity = new ResponseEntity<>("Access Denied", HttpStatus.UNAUTHORIZED);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(responseEntity.getBody().toString());
        //log.info("Response ");
    }
}
