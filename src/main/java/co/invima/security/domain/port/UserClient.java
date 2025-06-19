package co.invima.security.domain.port;

import co.invima.security.application.dto.GenericRequestDTO;
import co.invima.security.application.dto.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.net.URISyntaxException;

public interface UserClient {

    ResponseEntity<GenericResponseDTO> createUser(String person)  throws URISyntaxException, IOException, InterruptedException;

    ResponseEntity<GenericResponseDTO> searchById(Integer id)  throws URISyntaxException, IOException, InterruptedException;
    ResponseEntity<GenericResponseDTO> updatePassword(GenericRequestDTO genericRequestDTO)  throws URISyntaxException, IOException, InterruptedException;
    ResponseEntity<GenericResponseDTO> resetPassword(String email)  throws URISyntaxException, IOException, InterruptedException;
    ResponseEntity<GenericResponseDTO> searchByEmail(String email)  throws URISyntaxException, IOException, InterruptedException;
    ResponseEntity<GenericResponseDTO> getEmployeeDependencyRol(String json)  throws URISyntaxException, IOException, InterruptedException;
}
