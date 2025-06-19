package co.invima.security.infrastructure.adapter;

import co.invima.security.application.dto.MenuUriDTO;
import co.invima.security.application.dto.GenericResponseDTO;
import co.invima.security.domain.port.MenuClient;
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
public class HttpClientMenu implements MenuClient {

    @Autowired
    private MenuUriDTO menuUriDTO;
    private final HttpClient client;

    public HttpClientMenu() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }
    @Override
    public ResponseEntity<GenericResponseDTO> getMenuInformation(String datos) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        URI uri = new URI(menuUriDTO.getBase() + menuUriDTO.getPrefix() + menuUriDTO.getService());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(datos))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponseDTO genericResponseDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        ResponseEntity<GenericResponseDTO> httpResponse = ResponseEntity.status(response.statusCode()).body(genericResponseDTO);
        return httpResponse;
    }

    @Override
    public ResponseEntity<GenericResponseDTO> getMenuUserInfo(String datos) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        URI uri = new URI(menuUriDTO.getBase() + menuUriDTO.getPrefix() + menuUriDTO.getUserInfo());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(datos))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponseDTO genericResponseDTO = gson.fromJson(response.body(), GenericResponseDTO.class);
        ResponseEntity<GenericResponseDTO> httpResponse = ResponseEntity.status(response.statusCode()).body(genericResponseDTO);
        return httpResponse;
    }
}
