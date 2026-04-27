package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PROYECTO)
public class ProyectoEntidad {
    @Id
    @Column(name = CAMPO_IDENTIFICADOR, nullable = false, updatable = false)
    private UUID identificador;

    @Column(name = NUMERO_PROYECTO, nullable = false, length = 12)
    private String numeroProyecto;

    @Column(name = CAMPO_NOMBRE, nullable = false, length = 100)
    private String nombre;

    @Column(name = CAMPO_OBJETIVO, nullable = false, length = 500)
    private String objetivo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = PROYECTO, referencedColumnName = CAMPO_IDENTIFICADOR)
    private List<ProyectoAccionEntidad> acciones;
}