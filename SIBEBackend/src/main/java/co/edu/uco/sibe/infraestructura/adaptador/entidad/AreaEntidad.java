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
@Table(name = TABLA_AREA)
public class AreaEntidad {
    @Id
    @Column(name = CAMPO_IDENTIFICADOR, nullable = false, updatable = false)
    private UUID identificador;

    @Column(name = CAMPO_NOMBRE, nullable = false, length = 70)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = CAMPO_AREA)
    private List<SubareaEntidad> subareas;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = AREA_ACTIVIDAD,
            joinColumns = @JoinColumn(name = CAMPO_AREA, referencedColumnName = CAMPO_IDENTIFICADOR),
            inverseJoinColumns = @JoinColumn(name = CAMPO_ACTIVIDAD, referencedColumnName = CAMPO_IDENTIFICADOR)
    )
    private List<ActividadEntidad> actividades;
}