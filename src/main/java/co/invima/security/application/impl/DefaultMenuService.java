package co.invima.security.application.impl;

import co.invima.security.application.MenuService;
import co.invima.security.application.dto.MenuDTO;
import co.invima.security.application.dto.RoleMenuDTO;
import co.invima.security.application.dto.UserDTO;
import co.invima.security.application.dto.GenericResponseDTO;
import co.invima.security.domain.model.RoleMenu;
import co.invima.security.domain.port.MenuClient;
import co.invima.security.domain.port.RoleMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultMenuService implements MenuService {

    private final RoleMenuRepository menuRepository;
    private final MenuClient menuClient;

    @Autowired
    public DefaultMenuService(RoleMenuRepository menuRepository, MenuClient menuClient) {
        this.menuRepository = menuRepository;
        this.menuClient = menuClient;
    }
    @Override
    public ResponseEntity<List<RoleMenuDTO>> getMenuByRol(UserDTO userDTO) {

        List<RoleMenuDTO> roleMenuDTOs = new ArrayList<>();

        for (String role : userDTO.getRoles()) {
            Optional<List<RoleMenu>> roleMenus = menuRepository.getMenusByRole(role);

            // Mapea los menús y submenús a DTOs, respetando el orden
            List<MenuDTO> menuDTOs = roleMenus.get().stream()
                    .map(menu -> new MenuDTO(
                            menu.getMenu().getName(),
                            menu.getMenu().getUrl(),
                            menu.getMenu().getIcon(),
                            menu.getMenu().getOrderIndex(),
                            menu.getMenu().getSubMenus().stream()
                                    .map(subMenu -> new MenuDTO(
                                            subMenu.getName(),
                                            subMenu.getUrl(),
                                            subMenu.getIcon(),
                                            subMenu.getOrderIndex(),
                                            null  // Asume que los submenús no tienen más submenús
                                    ))
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
            roleMenuDTOs.add(new RoleMenuDTO(role, menuDTOs));
        }

        if (roleMenuDTOs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(roleMenuDTOs);
        }
    }

    @Override
    public ResponseEntity<GenericResponseDTO> getMenuInformation(String datos) throws Exception{

        return menuClient.getMenuInformation(datos);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> getMenuUserInfo(String datos) throws Exception {
        return menuClient.getMenuUserInfo(datos);
    }
}
