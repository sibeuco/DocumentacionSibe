package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TABLA_ACTIVIDAD)
public class ActividadEntidad {
    @Id
    @Column(name = CAMPO_IDENTIFICADOR, nullable = false, updatable = false)
    private UUID identificador;

    @Column(name = CAMPO_NOMBRE, nullable = false, length = 200)
    private String nombre;

    @Column(name = CAMPO_OBJETIVO, nullable = false, length = 500)
    private String objetivo;

    @Column(name = CAMPO_SEMESTRE, nullable = false, length = 6)
    private String semestre;

    @Column(name = CAMPO_RUTA_INSUMOS, nullable = false, length = 3000)
    private String rutaInsumos;

    @Column(name = CAMPO_FECHA_CREACION, nullable = false)
    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = CAMPO_INDICADOR)
    private IndicadorEntidad indicador;

    @JoinColumn(name = CAMPO_COLABORADOR, nullable = false)
    private UUID colaborador;
    
    @JoinColumn(name = CAMPO_CREADOR, nullable = false)
    private UUID creador;
}