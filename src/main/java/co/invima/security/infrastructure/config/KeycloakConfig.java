package co.invima.security.infrastructure.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak.client")
public class KeycloakConfig {

    private String id;
    private String secret;
    private String realm;
    private String authServerUrl;
    private String grantType;
    private String grantTypeApp;
    private String usersPath;
    private String user;
    private String password;

}
