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
@Table(name = TIPO_USUARIO)
public class TipoUsuarioEntidad {
    @Id
    private UUID identificador;

    @Column(length = 30, nullable = false)
    private String codigo;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column
    private boolean crear;

    @Column
    private boolean modificar;

    @Column
    private boolean eliminar;

    @Column
    private boolean consultar;
}