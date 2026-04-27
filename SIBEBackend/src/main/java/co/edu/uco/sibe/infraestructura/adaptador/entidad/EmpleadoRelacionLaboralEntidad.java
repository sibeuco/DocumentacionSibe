package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.CAMPO_RELACION_LABORAL;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.TABLA_EMPLEADO_RELACION_LABORAL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TABLA_EMPLEADO_RELACION_LABORAL)
public class EmpleadoRelacionLaboralEntidad {
    @Id
    private UUID identificador;

    @ManyToOne
    @JoinColumn(name = CAMPO_RELACION_LABORAL)
    private RelacionLaboralEntidad relacionLaboral;
}