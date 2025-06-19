package co.invima.security.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "invima_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "password", length = 100)
    private String password;
    @Column(name = "username", length = 100, unique = true, nullable = false)
    private String userName;
    @Column(name = "first_name", length = 100, nullable = false)
    private String name;
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastname;
    @Column(name = "born_date")
    private java.util.Date bornDate;
    @JoinColumn(name = "identification_type", foreignKey = @ForeignKey(name = "FK_Parametro_IdentificationType"), referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Parametro identificationType;
    @Column(name = "identification_number", unique = true, nullable = false)
    private String identificationNumber;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "cellphone_number")
    private String cellPhoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "profile")
    private String profile;
    @Column(name = "gender", unique = true, nullable = false)
    private String gender;
    @Column(name = "email_address", unique = true, nullable = false)
    private String emailAddress;
    @Column(name = "logical_status")
    private Boolean logicalStatus;
}
