package co.invima.security.application.impl;

import co.invima.security.application.AuthenticationService;
import co.invima.security.domain.model.Authentication;
import co.invima.security.application.dto.GenericResponseDTO;
import co.invima.security.domain.port.UserRepository;
import co.invima.security.infrastructure.config.KeycloakConfig;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class DefaultAuthenticationService implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KeycloakConfig keycloakConfig;


    @Override
    public ResponseEntity<Map> login(Authentication authentication) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakConfig.getId());
        body.add("client_secret", keycloakConfig.getSecret());
        body.add("grant_type", keycloakConfig.getGrantType());
        body.add("username", authentication.getUsername());
        body.add("password", authentication.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String tokenUrl = keycloakConfig.getAuthServerUrl() + "realms/" + keycloakConfig.getRealm() + "/protocol/openid-connect/token";
        return sendRequest(tokenUrl, body, headers);
    }

    @Override
    public ResponseEntity<Map> resfresh(String refreshToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakConfig.getId());
        body.add("client_secret", keycloakConfig.getSecret());
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String tokenUrl = keycloakConfig.getAuthServerUrl() + "realms/" + keycloakConfig.getRealm() + "/protocol/openid-connect/token";
        return sendRequest(tokenUrl, body, headers);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> logout(String refreshToken) {

        // Crear el cuerpo de la solicitud con los parámetros necesarios
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakConfig.getId());
        body.add("client_secret", keycloakConfig.getSecret());
        body.add("token", refreshToken);

        String url = keycloakConfig.getAuthServerUrl()  + "realms/" + keycloakConfig.getRealm() + "/protocol/openid-connect/logout";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<String> response= sendRequest(url, body, String.class, headers);

        return new ResponseEntity<>(GenericResponseDTO.builder().message("Logout successful")
                .objectResponse(response.getBody()).statusCode(response.getStatusCode().value()).build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> loginToken(Authentication authentication) throws Exception {
        ResponseEntity<Map> loginMap = login(authentication);

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Accept-Encoding", "gzip, deflate");
        header.add("Authorization", "Bearer "+ loginMap.getBody().get("access_token").toString());

        ResponseEntity<GenericResponseDTO> respuestaInformacion = getUserInfo(header);

        Object datosInfoEnObjeto = respuestaInformacion.getBody().getObjectResponse();
        String datosInfo = datosInfoEnObjeto.toString();
        String datosToken = new Gson().toJson(loginMap.getBody());
        String respuesta = datosInfo.replace(datosInfo.substring(datosInfo.length()-1), ",")+datosToken.substring(1);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(respuesta);

        JSONObject jsonIn = new JSONObject();
        JSONObject objOperacion = new JSONObject();
        objOperacion.put("usuarioRed", authentication.getUsername());
        jsonIn.put("objOperacion", objOperacion);

        String informacionFuncionario = userRepository.searchUser(jsonIn.toJSONString());

        JSONParser parser1 = new JSONParser();
        JSONObject json2 = (JSONObject) parser1.parse(informacionFuncionario);
        JSONObject respuestGenerica = (JSONObject) json2.get("respuesta");

        String codigo = (String) respuestGenerica.get("codigo");
        JSONArray mensaje = (JSONArray) json2.get("mensaje");

        if (codigo.equals("00") && mensaje != null && mensaje.size()>0) {
            json.put("userData", mensaje.get(0));
        }

        return new ResponseEntity<>(GenericResponseDTO.builder().message("datos token:")
                .objectResponse(json).statusCode(HttpStatus.OK.value()).build(), HttpStatus.OK);

    }

    private <T> ResponseEntity<T> sendRequest(String url, MultiValueMap<String, String> body, Class<T> responseType, MultiValueMap<String, String> headers) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, responseType);
    }

    private ResponseEntity<Map> sendRequest(String url, MultiValueMap<String, String> body, HttpHeaders headers) {
        return sendRequest(url, body, Map.class, headers);
    }

    private ResponseEntity<GenericResponseDTO> getUserInfo(MultiValueMap<String, String> header) {

        // Crear el cuerpo de la solicitud con los parámetros necesarios
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakConfig.getId());
        body.add("client_secret", keycloakConfig.getSecret());

        String url = keycloakConfig.getAuthServerUrl()  + "realms/" + keycloakConfig.getRealm() + "/protocol/openid-connect/userinfo";

        ResponseEntity<String> response= sendRequest(url, body, String.class, header);

        return new ResponseEntity<>(GenericResponseDTO.builder().message("datos token:")
                .objectResponse(response.getBody()).statusCode(response.getStatusCode().value()).build(), HttpStatus.OK);
    }

}
