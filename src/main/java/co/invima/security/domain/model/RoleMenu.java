package co.invima.security.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MA_menurolsistema")
public class RoleMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmenurolsistema", nullable = false)
    private Long id;

    @Column(name = "idrolsistema", nullable = false)
    private String role;

    @Column(name = "activo")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idmenusistema")
    private Menu menu;
}
