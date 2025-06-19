package co.invima.security.application;

import co.invima.security.domain.model.Authentication;
import co.invima.security.application.dto.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public interface AuthenticationService {

    ResponseEntity<Map> login(Authentication authentication);
    ResponseEntity<Map> resfresh(String refreshToken);
    ResponseEntity<GenericResponseDTO> logout(String refreshToken);
    ResponseEntity<GenericResponseDTO> loginToken(Authentication authentication) throws Exception;

}
