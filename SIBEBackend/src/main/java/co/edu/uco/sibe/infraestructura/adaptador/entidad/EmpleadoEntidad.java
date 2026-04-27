package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TABLA_EMPLEADO)
@PrimaryKeyJoinColumn(name = CAMPO_IDENTIFICADOR)
public class EmpleadoEntidad extends InternoEntidad {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = CAMPO_RELACION_LABORAL, nullable = false)
    private EmpleadoRelacionLaboralEntidad relacionLaboral;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = CAMPO_CENTRO_COSTOS, nullable = false)
    private EmpleadoCentroCostosEntidad centroCostos;

    public EmpleadoEntidad(
            UUID identificador,
            String nombreCompleto,
            String numeroIdentificacion,
            InternoCiudadResidenciaEntidad ciudadResidencia,
            String idCarnet,
            String sexo,
            EmpleadoRelacionLaboralEntidad relacionLaboral,
            EmpleadoCentroCostosEntidad centroCostos
    ) {
        super(identificador, nombreCompleto, numeroIdentificacion, ciudadResidencia, idCarnet, sexo);
        this.relacionLaboral = relacionLaboral;
        this.centroCostos = centroCostos;
    }
}