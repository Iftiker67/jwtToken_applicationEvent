package jwt.token.JWTtoken.config.response;

public class JwtAuthResponse {
    private String jwt;

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }

    public JwtAuthResponse(){

    }
    public JwtAuthResponse(String jwt) {
        this.jwt = jwt;
    }


}
