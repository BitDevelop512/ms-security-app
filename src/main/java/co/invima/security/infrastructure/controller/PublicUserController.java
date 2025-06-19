package co.invima.security.infrastructure.controller;

import co.invima.security.application.UserService;
import co.invima.security.application.dto.GenericRequestDTO;
import co.invima.security.application.dto.GenericResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/public/v1/user")
@CrossOrigin(origins = "*")
public class PublicUserController {

    @Autowired
    private UserService userInfo;

    @PostMapping("/person")
    public ResponseEntity<GenericResponseDTO> createUser(@RequestBody String person) throws Exception {
        return userInfo.createUser(person);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> userId(@PathVariable Integer id) throws Exception {
        return userInfo.searchById(id);
    }

    @PutMapping("/pwd")
    public ResponseEntity<GenericResponseDTO> updatePassword(@RequestBody GenericRequestDTO genericRequestDTO) throws Exception {
        return userInfo.updatePassword(genericRequestDTO);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<GenericResponseDTO> resetPassword(@RequestBody String email) throws Exception {
        return userInfo.resetPassword(email);
    }

}
