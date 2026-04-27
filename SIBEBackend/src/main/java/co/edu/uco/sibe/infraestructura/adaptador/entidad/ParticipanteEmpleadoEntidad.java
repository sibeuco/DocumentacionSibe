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
@Table(name = PARTICIPANTE_EMPLEADO)
@PrimaryKeyJoinColumn(name = CAMPO_IDENTIFICADOR)
public class ParticipanteEmpleadoEntidad extends ParticipanteInternoEntidad {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = RELACION_LABORAL, nullable = false)
    private EmpleadoRelacionLaboralEntidad relacionLaboral;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CAMPO_CENTRO_COSTOS, nullable = false)
    private EmpleadoCentroCostosEntidad centroCostos;

    public ParticipanteEmpleadoEntidad(
            UUID identificador,
            MiembroEntidad miembro,
            InternoCiudadResidenciaEntidad ciudadResidencia,
            String idCarnet,
            String sexo,
            EmpleadoRelacionLaboralEntidad relacionLaboral,
            EmpleadoCentroCostosEntidad centroCostos
    ) {
        super(identificador, miembro, ciudadResidencia, idCarnet, sexo);
        this.relacionLaboral = relacionLaboral;
        this.centroCostos = centroCostos;
    }
}