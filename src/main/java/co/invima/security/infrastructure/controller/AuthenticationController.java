package co.invima.security.infrastructure.controller;

import co.invima.security.application.AuthenticationService;
import co.invima.security.domain.model.Authentication;
import co.invima.security.application.dto.GenericResponseDTO;
import co.invima.security.domain.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String TOKEN_TYPE = "token_type";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String EXPIRES_IN = "expires_in";
    private static final String SCOPE = "scope";

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestParam String refreshToken) {
        ResponseEntity<Map> response = authenticationService.resfresh(refreshToken);

        if (response.getStatusCode().is2xxSuccessful()) {

            return ResponseEntity.ok( getToken(response));
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<GenericResponseDTO> logout(@RequestParam("refreshToken") String refreshToken) {

        return authenticationService.logout(refreshToken);
    }

    private static Token getToken(ResponseEntity<Map> response) {
        Map<String, Object> responseBody = response.getBody();
        Token token = new Token();
        token.setAccessToken((String) responseBody.get(ACCESS_TOKEN));
        token.setTokenType((String)responseBody.get(TOKEN_TYPE));
        token.setRefreshToken((String)responseBody.get(REFRESH_TOKEN));
        token.setExpiresIn((Integer) responseBody.get(EXPIRES_IN));
        token.setScope((String)responseBody.get(SCOPE));
        return token;
    }

    @PostMapping("/loginToken")
    public ResponseEntity<GenericResponseDTO> loginToken(@RequestBody Authentication authentication)
            throws Exception {
        return authenticationService.loginToken(authentication);
    }

}
