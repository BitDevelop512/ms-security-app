package co.invima.security.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RoleMenuDTO {

    private String role;

    private List<MenuDTO> menu;

    public RoleMenuDTO(String role, List<MenuDTO> menu) {
        this.role = role;
        this.menu = menu;
    }
}
