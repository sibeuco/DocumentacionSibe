package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import java.util.UUID;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.RELACION_LABORAL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = RELACION_LABORAL)
public class RelacionLaboralEntidad {
    @Id
    private UUID identificador;

    @Column(length = 4, nullable = false)
    private String codigo;

    @Column(length = 20, nullable = false)
    private String descripcion;
}