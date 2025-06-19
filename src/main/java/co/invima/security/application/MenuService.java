package co.invima.security.application;

import co.invima.security.application.dto.RoleMenuDTO;
import co.invima.security.application.dto.UserDTO;
import co.invima.security.application.dto.GenericResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuService {
    ResponseEntity<List<RoleMenuDTO>> getMenuByRol(UserDTO userDTO);
    ResponseEntity<GenericResponseDTO> getMenuInformation(String datos) throws Exception;
    ResponseEntity<GenericResponseDTO> getMenuUserInfo(String datos) throws Exception;
}
