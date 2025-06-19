package co.invima.security.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class UserKeycloak implements Serializable {

    private String username;
    private String email;
    private Boolean enabled;
    private String firstName;
    private String lastName;

    public UserKeycloak(String username, String email, Boolean enabled, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
