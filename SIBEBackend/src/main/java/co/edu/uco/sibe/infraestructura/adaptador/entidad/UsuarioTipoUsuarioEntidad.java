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
@Table(name = TABLA_USUARIO_TIPO_USUARIO)
public class UsuarioTipoUsuarioEntidad {
    @Id
    private UUID identificador;

    @ManyToOne
    @JoinColumn(name = TIPO_USUARIO)
    private TipoUsuarioEntidad tipoUsuario;
}