package co.invima.security.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class UserCreateDTO implements Serializable {

    private int idTipoDocumento;
    private String numeroDocumento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correoElectronico;
    private String usuarioCrea;
    private int idPais;
    private String tipoUsuario;
}
