package co.invima.security.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDTO {

    private String email;
    private List<String> roles;
    private java.lang.Integer id;
    private java.lang.String userId;
    private String userName;
    private java.lang.String password;
    private java.lang.String firstName;
    private java.lang.String lastname;
    private java.lang.String fullName;
    private java.util.Date bornDate;
    private java.lang.String identificationType;
    private java.lang.String identificationNumber;
    private java.lang.String phoneNumber;
    private java.lang.String cellPhoneNumber;
    private java.lang.String address;
    private java.lang.String profile;
    private java.lang.String gender;
    private java.lang.String emailAddress;
    private java.lang.Long createdTimestamp;
    private java.lang.Boolean enabled;
    private java.lang.Boolean totp;
    private java.lang.Boolean emailVerified;
    private java.lang.String parent;
    private java.util.List<java.lang.String> disableCredentialTypes;
    private java.util.List<java.lang.String> requiredActions;
    private java.lang.String notBefore;
    private AccessDTO access;
    private java.util.List<CredentialsDTO> credentials;

    public UserDTO() {

    }
}
