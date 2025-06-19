package co.invima.security.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class MenuDTO {
    private String name;
    private String url;
    private String icon;
    private String order;
    private List<MenuDTO> subMenus;

    public MenuDTO(String name, String url, String icon, String order, List<MenuDTO> subMenus) {
        this.name = name;
        this.url = url;
        this.icon = icon;
        this.order = order;
        this.subMenus = subMenus;
    }
}
