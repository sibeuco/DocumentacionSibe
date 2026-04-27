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
@Table(name =TIPO_INDICADOR)
public class TipoIndicadorEntidad {
    @Id
    private UUID identificador;

    @Column(name = NATURALEZA, length = 20, nullable = false)
    private String naturaleza;

    @Column(name = TIPOLOGIA, length = 15, nullable = false)
    private String tipologia;
}