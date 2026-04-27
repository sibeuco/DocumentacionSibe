package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.PUBLICO_INTERES;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PUBLICO_INTERES)
public class PublicoInteresEntidad {
    @Id
    private UUID identificador;

    @Column(length = 50, nullable = false)
    private String nombre;
}