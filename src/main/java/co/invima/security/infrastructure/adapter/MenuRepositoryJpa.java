package co.invima.security.infrastructure.adapter;

import co.invima.security.domain.model.RoleMenu;
import co.invima.security.domain.port.RoleMenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepositoryJpa extends JpaRepository<RoleMenu, Long>, RoleMenuRepository {

    Optional<List<RoleMenu>> getMenusByRole(String roleName);
    default List<RoleMenu> getAll() {
        return findAll();
    }

}
