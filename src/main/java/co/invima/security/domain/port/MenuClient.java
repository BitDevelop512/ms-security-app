package co.invima.security.domain.port;

import co.invima.security.application.dto.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.net.URISyntaxException;

public interface MenuClient {

    ResponseEntity<GenericResponseDTO> getMenuInformation(String datos) throws URISyntaxException, IOException, InterruptedException;
    ResponseEntity<GenericResponseDTO> getMenuUserInfo(String datos) throws URISyntaxException, IOException, InterruptedException;
}
