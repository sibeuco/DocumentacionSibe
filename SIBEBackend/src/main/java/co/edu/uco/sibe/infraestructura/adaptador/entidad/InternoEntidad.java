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
@Table(name = INTERNO)
@PrimaryKeyJoinColumn(name = CAMPO_IDENTIFICADOR)
public class InternoEntidad extends MiembroEntidad {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = CIUDAD_RESIDENCIA, nullable = false)
    private InternoCiudadResidenciaEntidad ciudadResidencia;

    @Column(name = ID_CARNET, nullable = false, length = 20)
    private String idCarnet;

    @Column(name = SEXO, nullable = false, length = 1)
    private String sexo;

    public InternoEntidad(UUID identificador, String nombreCompleto, String numeroIdentificacion, InternoCiudadResidenciaEntidad ciudadResidencia, String idCarnet, String sexo) {
        super(identificador, nombreCompleto, numeroIdentificacion);
        this.ciudadResidencia = ciudadResidencia;
        this.idCarnet = idCarnet;
        this.sexo = sexo;
    }
}