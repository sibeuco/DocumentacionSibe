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
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.TIPO_IDENTIFICACION;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TIPO_IDENTIFICACION)
public class TipoIdentificacionEntidad {
    @Id
    private UUID identificador;

    @Column(length = 6, nullable = false)
    private String sigla;

    @Column(length = 30, nullable = false)
    private String descripcion;
}