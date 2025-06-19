package co.invima.security.infrastructure.adapter;

import co.invima.security.domain.model.User;
import co.invima.security.domain.port.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, Integer>, UserRepository {
    @Procedure("Maestra.dbo.USP_ConsultarUsuario_S")
    String searchUser(String json);
}
