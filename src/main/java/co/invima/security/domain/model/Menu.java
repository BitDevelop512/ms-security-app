package co.invima.security.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "MA_menusistema")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmenu", nullable = false)
    private Long id;

    @Column(name = "nombremenu")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "icono")
    private String icon;

    @Column(name = "orden")
    private String orderIndex;

    @Column(name = "activo")
    private Boolean status;

    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> subMenus;

    @ManyToOne
    @JoinColumn(name = "idmenupadre")
    private Menu parentMenu;
}
