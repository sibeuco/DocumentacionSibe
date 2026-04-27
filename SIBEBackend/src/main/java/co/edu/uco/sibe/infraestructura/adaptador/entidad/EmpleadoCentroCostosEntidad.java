package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.EMPLEADO_CENTRO_COSTOS;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EMPLEADO_CENTRO_COSTOS)
public class EmpleadoCentroCostosEntidad {
    @Id
    private UUID identificador;

    @ManyToOne
    @JoinColumn(name = EMPLEADO_CENTRO_COSTOS)
    private CentroCostosEntidad centroCostos;
}