package co.invima.security.infrastructure.adapter;

import co.invima.security.application.dto.GenericRequestDTO;
import co.invima.security.application.dto.GenericResponseDTO;
import co.invima.security.application.dto.UserUriDTO;
import co.invima.security.domain.port.UserClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class HttpClientUser implements UserClient {

    @Autowired
    private UserUriDTO userUriDTO;
    private final HttpClient client;

    public HttpClientUser() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public ResponseEntity<GenericResponseDTO> createUser(String person) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        URI uri = new URI(userUriDTO.getBase()
                + userUriDTO.getPrefix()
                + userUriDTO.getService());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(person))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponseDTO genericResponseObjectDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        return ResponseEntity.status(response.statusCode()).body(genericResponseObjectDTO);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> searchById(Integer id) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        URI uri = new URI(userUriDTO.getBase()
                + userUriDTO.getPrefix()
                + userUriDTO.getSearchbyid()
                +"/"
                + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponseDTO genericResponseObjectDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        return ResponseEntity.status(response.statusCode()).body(genericResponseObjectDTO);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> updatePassword(GenericRequestDTO genericRequestDTO) throws URISyntaxException, IOException, InterruptedException {
        // Convertir el objeto a JSON
        Gson gsonBody = new Gson();
        String requestBody = gsonBody.toJson(genericRequestDTO);

        Gson gson = new Gson();
        URI uri = new URI(userUriDTO.getBase()
                + userUriDTO.getPrefix()
                + userUriDTO.getUpdatepassword());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponseDTO genericResponseObjectDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        return ResponseEntity.status(response.statusCode()).body(genericResponseObjectDTO);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> resetPassword(String email) throws URISyntaxException, IOException, InterruptedException {

        Gson gson = new Gson();
        URI uri = new URI(userUriDTO.getBase()
                + userUriDTO.getPrefix()
                + userUriDTO.getResetPassword());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(email))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponseDTO genericResponseObjectDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        return ResponseEntity.status(response.statusCode()).body(genericResponseObjectDTO);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> searchByEmail(String email) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        URI uri = new URI(userUriDTO.getBase()
                + userUriDTO.getPrefix()
                + userUriDTO.getSearchByEmail()
                +"/"
                + email);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponseDTO genericResponseObjectDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        return ResponseEntity.status(response.statusCode()).body(genericResponseObjectDTO);

    }

    @Override
    public ResponseEntity<GenericResponseDTO> getEmployeeDependencyRol(String json) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        URI uri = new URI(userUriDTO.getBase()
                + userUriDTO.getPrefix()
                + userUriDTO.getEmployeeDependencyRol());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponseDTO genericResponseObjectDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        return ResponseEntity.status(response.statusCode()).body(genericResponseObjectDTO);
    }
}
