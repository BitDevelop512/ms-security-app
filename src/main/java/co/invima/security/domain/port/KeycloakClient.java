package co.invima.security.domain.port;

import co.invima.security.application.dto.GenericResponseDTO;
import co.invima.security.application.dto.UserPasswordKeycloakDTO;
import org.springframework.http.ResponseEntity;

public interface KeycloakClient {

    ResponseEntity<GenericResponseDTO> createUser(String persons)  throws Exception;
    ResponseEntity<GenericResponseDTO> updatePwd(UserPasswordKeycloakDTO userPasswordKeycloakDTO)  throws Exception;
    String getUserId(String email) throws Exception;

}
