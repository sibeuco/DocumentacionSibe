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
@Table(name = TABLA_CENTRO_COSTOS)
public class CentroCostosEntidad {
    @Id
    private UUID identificador;

    @Column(length = 6, nullable = false)
    private String codigo;

    @Column(length = 100, nullable = false)
    private String descripcion;
}