package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@Entity
@Table(name = EXTERNO)
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = CAMPO_IDENTIFICADOR)
public class ExternoEntidad extends MiembroEntidad {
    public ExternoEntidad(UUID identificador, String nombreCompleto, String numeroIdentificacion) {
        super(identificador, nombreCompleto, numeroIdentificacion);
    }
}