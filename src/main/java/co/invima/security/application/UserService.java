package co.invima.security.application;

import co.invima.security.application.dto.GenericRequestDTO;
import co.invima.security.application.dto.GenericResponseDTO;
import co.invima.security.application.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

public interface UserService {

    ResponseEntity<UserDTO> getUserInfo(Jwt jwt);
    ResponseEntity<GenericResponseDTO> createUser(String person) throws Exception;
    ResponseEntity<GenericResponseDTO> searchById(Integer id) throws Exception;
    ResponseEntity<GenericResponseDTO> updatePassword(GenericRequestDTO genericRequestDTO) throws Exception;
    ResponseEntity<GenericResponseDTO> resetPassword(String email) throws Exception;
    ResponseEntity<GenericResponseDTO> searchByEmail(String email) throws Exception;
    ResponseEntity<GenericResponseDTO> getEmployeeDependencyRol(String json) throws Exception;
}
