package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.CAMPO_IDENTIFICADOR;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.PARTICIPANTE_EXTERNO;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = PARTICIPANTE_EXTERNO)
@PrimaryKeyJoinColumn(name = CAMPO_IDENTIFICADOR)
public class ParticipanteExternoEntidad extends ParticipanteEntidad {
    public ParticipanteExternoEntidad(UUID identificador, MiembroEntidad miembro) {
        super(identificador, miembro);
    }
}