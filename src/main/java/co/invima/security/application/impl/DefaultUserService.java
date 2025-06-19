package co.invima.security.application.impl;

import co.invima.security.application.UserService;
import co.invima.security.application.dto.*;
import co.invima.security.domain.port.KeycloakClient;
import co.invima.security.domain.port.UserClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;

@Service
public class DefaultUserService implements UserService {

    public static final String PREFERRED_USERNAME = "preferred_username";
    public static final String EMAIL = "email";
    public static final String ROLES = "resource_access.my-client.roles";
    @Autowired
    private UserClient userClient;
    @Autowired
    private KeycloakClient keycloakClient;

    @Override
    public ResponseEntity<UserDTO> getUserInfo(Jwt jwt) {
        // Extraer la información del usuario del token JWT
        UserDTO userInfo = new UserDTO();
        userInfo.setUserName(jwt.getClaim(PREFERRED_USERNAME));
        userInfo.setEmail(jwt.getClaim(EMAIL));
        userInfo.setRoles(jwt.getClaimAsStringList(ROLES));  // Asegúrate que roles esté en el token
        userInfo.setRoles(Arrays.asList("1","2"));

        return ResponseEntity.ok(userInfo);
    }

    public ResponseEntity<GenericResponseDTO> createUser(String person) throws Exception {
        ResponseEntity<GenericResponseDTO> request = userClient.createUser(person);

        createUserKeyCloak(person, null);
        return request;
    }

    @Override
    public ResponseEntity<GenericResponseDTO> searchById(Integer id) throws Exception {
        return userClient.searchById(id);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> updatePassword(GenericRequestDTO genericRequestDTO) throws Exception {
        ResponseEntity<GenericResponseDTO> response = userClient.updatePassword(genericRequestDTO);
        if(response.getStatusCode().is2xxSuccessful()) {
            String email = (String) ((LinkedHashMap) genericRequestDTO.getRequest()).get("usuarioRed");
            String password = (String) ((LinkedHashMap) genericRequestDTO.getRequest()).get("password");
            Integer userId = Integer.parseInt((String) ((LinkedHashMap) genericRequestDTO.getRequest()).get("id"));
            UserPasswordKeycloakDTO userPasswordKeycloakDTO = new UserPasswordKeycloakDTO();
            userPasswordKeycloakDTO.setPassword(password);
            userPasswordKeycloakDTO.setEmail(URLEncoder.encode(email, StandardCharsets.UTF_8.toString()));
            this.createUserForUpdatePassword(email, userId);
            ResponseEntity<GenericResponseDTO> responseKeycloak=keycloakClient.updatePwd(userPasswordKeycloakDTO);
            System.out.println("updatePassword: keycloakClient.updatePwd[" + responseKeycloak.getStatusCode()+"]");
            if(responseKeycloak.getStatusCode().is2xxSuccessful()){
                return response;
            }else{
                return responseKeycloak;
            }
        }
        return response;
    }

    /*
    Cuando se actualiza la contraseña y no existe el usuario en keycloak, lo crea.
    (flujo de crear usuarios desde el registrar empresa)
     */
    private void createUserForUpdatePassword(String email, Integer userId) {
        try{
            String userIdKeyCloak = this.keycloakClient.getUserId(email);
            System.out.println("getUserId[" +userIdKeyCloak+"]");

            if(userIdKeyCloak == null){
                System.out.println("user existe, init create]");
                ResponseEntity<GenericResponseDTO> response=this.searchById(userId);
                Gson gson = new Gson();
                createUserKeyCloak(gson.toJson(response.getBody().getObjectResponse()),
                        email);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }

    private void createUserKeyCloak(String persona, String email) throws Exception {
        Gson gson = new Gson();
        UserCreateDTO userCreateDTO = gson.fromJson(persona, UserCreateDTO.class);
        UserKeycloak userKeycloak = new UserKeycloak(
                email != null ? email : userCreateDTO.getCorreoElectronico(),
                email != null ? email : userCreateDTO.getCorreoElectronico(),
                true,
                userCreateDTO.getPrimerNombre(),
                userCreateDTO.getPrimerApellido());
        String json = gson.toJson(userKeycloak);
        System.out.println("send to create user in keycloak");
        keycloakClient.createUser(json);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> resetPassword(String email) throws Exception {
        return userClient.resetPassword(email);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> searchByEmail(String email) throws Exception {
        return userClient.searchByEmail(email);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> getEmployeeDependencyRol(String json) throws Exception {
        return userClient.getEmployeeDependencyRol(json);
    }

}
