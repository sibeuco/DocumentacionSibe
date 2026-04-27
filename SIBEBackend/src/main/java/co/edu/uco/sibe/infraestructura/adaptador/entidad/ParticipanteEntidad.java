package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.MIEMBRO;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.PARTICIPANTE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PARTICIPANTE)
@Inheritance(strategy = InheritanceType.JOINED)
public class ParticipanteEntidad {
    @Id
    private UUID identificador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = MIEMBRO, nullable = false)
    private MiembroEntidad miembro;
}