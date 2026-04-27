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
@Table(name = PARTICIPANTE_INTERNO)
@PrimaryKeyJoinColumn(name = CAMPO_IDENTIFICADOR)
public class ParticipanteInternoEntidad extends ParticipanteEntidad {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CIUDAD_RESIDENCIA, nullable = false)
    private InternoCiudadResidenciaEntidad ciudadResidencia;

    @Column(name = ID_CARNET, nullable = false, length = 20)
    private String idCarnet;

    @Column(name = SEXO, nullable = false, length = 1)
    private String sexo;

    public ParticipanteInternoEntidad(UUID identificador, MiembroEntidad miembro, InternoCiudadResidenciaEntidad ciudadResidencia, String idCarnet, String sexo) {
        super(identificador, miembro);
        this.ciudadResidencia = ciudadResidencia;
        this.idCarnet = idCarnet;
        this.sexo = sexo;
    }
}
