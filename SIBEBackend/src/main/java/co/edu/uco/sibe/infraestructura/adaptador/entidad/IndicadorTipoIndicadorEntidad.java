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
@Table(name = INDICADOR_TIPO_INDICADOR)
public class IndicadorTipoIndicadorEntidad {
    @Id
    @Column(name = CAMPO_IDENTIFICADOR, nullable = false, updatable = false)
    private UUID identificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = TIPO_INDICADOR, nullable = false)
    private TipoIndicadorEntidad tipoIndicador;
}