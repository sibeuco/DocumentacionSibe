package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TABLA_DIRECCION)
public class DireccionEntidad {
    @Id
    @Column(name = CAMPO_IDENTIFICADOR, nullable = false, updatable = false)
    private UUID identificador;

    @Column(name = CAMPO_NOMBRE, nullable = false, length = 70)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = CAMPO_DIRECCION) // Columna FK en AreaEntidad
    private List<AreaEntidad> areas;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = DIRECCION_ACTIVIDAD,
            joinColumns = @JoinColumn(name = CAMPO_DIRECCION, referencedColumnName = CAMPO_IDENTIFICADOR),
            inverseJoinColumns = @JoinColumn(name = CAMPO_ACTIVIDAD, referencedColumnName = CAMPO_IDENTIFICADOR)
    )
    private List<ActividadEntidad> actividades;
}