package co.invima.security.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
@Data
public class CredentialsDTO implements Serializable {
    private java.lang.String type;
    private java.lang.String value;
    private java.lang.Boolean temporary;
}
