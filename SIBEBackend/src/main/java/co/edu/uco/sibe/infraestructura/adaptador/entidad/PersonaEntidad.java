package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.IDENTIFICACION;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.PERSONA;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PERSONA)
public class PersonaEntidad {
    @Id
    private UUID identificador;

    @Column(length = 50, nullable = false)
    private String nombres;

    @Column(length = 50, nullable = false)
    private String apellidos;

    @Column(length = 100, nullable = false)
    private String correo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = IDENTIFICACION, nullable = false)
    private IdentificacionEntidad identificacion;
}