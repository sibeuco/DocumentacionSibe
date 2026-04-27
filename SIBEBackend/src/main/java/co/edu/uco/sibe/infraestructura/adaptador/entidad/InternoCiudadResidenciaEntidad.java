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
@Table(name = INTERNO_CIUDAD_RESIDENCIA)
public class InternoCiudadResidenciaEntidad {
    @Id
    private UUID identificador;

    @ManyToOne
    @JoinColumn(name = CIUDAD_RESIDENCIA)
    private CiudadResidenciaEntidad ciudadResidencia;
}