package co.invima.security.infrastructure.controller;

import co.invima.security.application.MenuService;
import co.invima.security.application.dto.RoleMenuDTO;
import co.invima.security.application.dto.UserDTO;
import co.invima.security.application.dto.GenericResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/v1/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("")
    public ResponseEntity<List<RoleMenuDTO>> getMenuByRol(@RequestBody UserDTO userDTO) {

        return menuService.getMenuByRol(userDTO);
    }

    @PostMapping("/informacion")
    public ResponseEntity<GenericResponseDTO> getMenuInformation(@RequestBody String datos) throws Exception{
        return menuService.getMenuInformation(datos);
    }

    @PostMapping("/user/info")
    public ResponseEntity<GenericResponseDTO> getMenuUserInfo(@RequestBody String datos) throws Exception{
        return menuService.getMenuUserInfo(datos);
    }
}
