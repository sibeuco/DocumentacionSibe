package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PROYECTO_ACCION)
public class ProyectoAccionEntidad {
    @Id
    @Column(name = CAMPO_IDENTIFICADOR, updatable = false)
    private UUID identificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ACCION, nullable = false)
    private AccionEntidad accion;
}