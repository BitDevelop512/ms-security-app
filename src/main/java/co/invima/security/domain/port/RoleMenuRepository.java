package co.invima.security.domain.port;

import co.invima.security.domain.model.RoleMenu;
import java.util.List;
import java.util.Optional;

public interface RoleMenuRepository {

    Optional<List<RoleMenu>> getMenusByRole(String roleName);
    List<RoleMenu> getAll();
}
