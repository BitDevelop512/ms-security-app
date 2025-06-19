package co.invima.security.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int expiresIn;
    private String scope;
    private String refreshExpiresIn;
}
