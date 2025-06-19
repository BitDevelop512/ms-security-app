package co.invima.security.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
@ToString
@Data
@Builder
public class GenericResponseDTO implements Serializable {
    private Object message;
    private Object objectResponse;
    private Integer totalRegistros;
    private Integer statusCode;


}
