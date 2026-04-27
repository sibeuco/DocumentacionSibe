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
@Table(name = USUARIO_ORGANIZACION)
public class UsuarioOrganizacionEntidad {
    @Id
    private UUID identificador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = USUARIO, nullable = false)
    private UsuarioEntidad usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CAMPO_DIRECCION)
    private DireccionEntidad direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CAMPO_AREA)
    private AreaEntidad area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = SUB_AREA)
    private SubareaEntidad subarea;
}