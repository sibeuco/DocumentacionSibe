package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import java.util.UUID;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.TABLA_ESTADO_ACTIVIDAD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TABLA_ESTADO_ACTIVIDAD)
public class EstadoActividadEntidad {
    @Id
    private UUID identificador;

    @Column(length = 15, nullable = false)
    private String nombre;
}