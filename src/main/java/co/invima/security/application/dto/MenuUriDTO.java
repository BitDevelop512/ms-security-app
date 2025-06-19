package co.invima.security.application.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Data
@Configuration
@ConfigurationProperties(prefix = "menu.client")
public class MenuUriDTO {

    private String base;
    private String prefix;
    private String service;
    private String userInfo;
}
