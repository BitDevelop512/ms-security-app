package co.invima.security.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "parametro")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "parent"})
public class Parametro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true)
    private Integer id;
    @JoinColumn(name = "tipo", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_Parametro_Tipo"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Parametro tipo;
    @JoinColumn(name = "padre", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_Parametro_Padre"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Parametro padre;
    @OneToMany(mappedBy = "padre")
    private List<Parametro> hijo;
    @Column(name = "descripcion", unique = true, nullable = false)
    private String descripcion;
    @Column(name = "codigo", length = 100)
    @Basic(optional = false)
    private String codigo;
    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;
    @Column(name = "logical_status")
    private Boolean logicalStatus;
}
