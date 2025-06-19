package co.invima.security.infrastructure.controller;

import co.invima.security.application.UserService;
import co.invima.security.application.dto.GenericResponseDTO;
import co.invima.security.application.dto.UserDTO;
import co.invima.security.infrastructure.controller.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userInfo;

    private final String secretKey = "1234567890123456";

    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUserInfo(@AuthenticationPrincipal Jwt jwt) {

        return userInfo.getUserInfo(jwt);
    }

    @GetMapping("/{encr}")
    public ResponseEntity<String> getUserInfo(@PathVariable String encr) throws Exception {

        String encryptedResponse = EncryptionUtil.decrypt(encr, secretKey);
        return ResponseEntity.ok(encryptedResponse);
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<GenericResponseDTO> searchByEmail(@PathVariable String email) throws Exception {
        return userInfo.searchByEmail(email);
    }

    @PostMapping("/employeeDependencyRol")
    public ResponseEntity<GenericResponseDTO> getEmployeeDependencyRol(@RequestBody String json) throws Exception {
        return userInfo.getEmployeeDependencyRol(json);
    }
}
