package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.PETICION_RECUPERACION_CLAVE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PETICION_RECUPERACION_CLAVE)
public class PeticionRecuperacionClaveEntidad {
    @Id
    private UUID identificador;

    @Column(nullable = false)
    private String codigo;

    @Column(length = 100, nullable = false)
    private String correo;

    private LocalDateTime fecha;
}