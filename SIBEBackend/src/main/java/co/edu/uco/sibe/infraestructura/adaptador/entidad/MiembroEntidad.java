package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import java.util.UUID;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = MIEMBRO)
@Inheritance(strategy = InheritanceType.JOINED)
public class MiembroEntidad {
    @Id
    @Column(name = CAMPO_IDENTIFICADOR, nullable = false, updatable = false)
    private UUID identificador;

    @Column(name = NOMBRE_COMPLETO, nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = NUMERO_IDENTIFICACION, nullable = false, length = 12)
    private String numeroIdentificacion;

    @Column(name = ESTA_ACTIVO, nullable = false)
    private boolean estaActivo = true;

    public MiembroEntidad(UUID identificador, String nombreCompleto, String numeroIdentificacion) {
        this.identificador = identificador;
        this.nombreCompleto = nombreCompleto;
        this.numeroIdentificacion = numeroIdentificacion;
        this.estaActivo = true;
    }
}