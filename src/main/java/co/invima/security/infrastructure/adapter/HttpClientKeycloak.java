package co.invima.security.infrastructure.adapter;

import co.invima.security.application.dto.GenericResponseDTO;
import co.invima.security.application.dto.UserPasswordKeycloakDTO;
import co.invima.security.domain.port.KeycloakClient;
import co.invima.security.infrastructure.config.KeycloakConfig;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class HttpClientKeycloak implements KeycloakClient {

    @Autowired
    private KeycloakConfig keycloakConfig;
    private final HttpClient client;

    public HttpClientKeycloak() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public ResponseEntity<GenericResponseDTO> createUser(String persons) throws Exception {

        Gson gson = new Gson();
        URI uri = new URI(keycloakConfig.getAuthServerUrl()
                + "admin/"
                + "realms/"
                + keycloakConfig.getRealm()
                + keycloakConfig.getUsersPath());
        System.out.println("persona::: "+ persons);
        System.out.println("url keycloak::: " + uri.toString());
        String token= getAccessToken();
        System.out.println( "Token::: " + token);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(persons))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("usuario creado: " + response);
        GenericResponseDTO genericResponseDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        return ResponseEntity.status(response.statusCode()).body(genericResponseDTO);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> updatePwd(UserPasswordKeycloakDTO userPasswordKeycloakDTO) throws Exception {
        System.out.println("HttpClientKeycloak.updatePwd");
        String userId = this.getUserId(userPasswordKeycloakDTO.getEmail());
        if(userId == null){
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message("No existe usuario en keycloak, contacte al administrador")
                    .objectResponse(null).statusCode(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
        }
        Gson gson = new Gson();
        URI uri = new URI(keycloakConfig.getAuthServerUrl()
                + "admin/"
                + "realms/"
                + keycloakConfig.getRealm()
                + keycloakConfig.getUsersPath()
                + "/"
                + userId);

        String requestBody = """
                {
                  "credentials": [
                    {
                      "type": "password",
                      "value": "%s",
                      "temporary": false
                    }
                  ]
                }
                """.formatted(userPasswordKeycloakDTO.getPassword());


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + getAccessToken())
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("HttpClientKeycloak.updatePwd: [" + response.statusCode()+"]");
        GenericResponseDTO genericResponseDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        return ResponseEntity.status(response.statusCode()).body(genericResponseDTO);
    }


    public String getUserId(String email) throws Exception {
        System.out.println("HttpClientKeycloak.getUserId");

        URI uri = new URI(keycloakConfig.getAuthServerUrl()
                + "admin/"
                + "realms/"
                + keycloakConfig.getRealm()
                + keycloakConfig.getUsersPath()
                + "?email="
                + email);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + getAccessToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        if (response.statusCode() == 200) {
            if (response.body() == null || response.body().isBlank()) {
                System.out.println("No se encontró ningún usuario con el email: " + email);
                return null;
            }

            Gson gson = new Gson();
            List<Map<String, Object>> responseBody = gson.fromJson(response.body(), List.class);

            if (responseBody.isEmpty()) {
                System.out.println("No se encontró ningún usuario con el email: " + email);
                return null;
            }

            return (String) responseBody.get(0).get("id");
        } else {
            System.out.println("No se encontró ningún usuario con el email: " + email);
            return null;
        }
    }

    private String getAccessToken() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = "client_id=" + URLEncoder.encode(keycloakConfig.getId(), StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode(keycloakConfig.getSecret(), StandardCharsets.UTF_8)
                + "&grant_type=" + URLEncoder.encode(keycloakConfig.getGrantTypeApp(), StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(keycloakConfig.getAuthServerUrl()
                        + "realms/"
                        + keycloakConfig.getRealm()
                        + "/protocol/openid-connect/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            Map<String, Object> responseBody = gson.fromJson(response.body(), Map.class);
            return (String) responseBody.get("access_token");
        } else {
            throw new RuntimeException("Error al obtener el token: " + response.statusCode() + " " + response.body());
        }
    }
}
