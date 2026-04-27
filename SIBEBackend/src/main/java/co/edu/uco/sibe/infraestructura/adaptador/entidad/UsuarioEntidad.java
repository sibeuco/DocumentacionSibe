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
@Table(name = USUARIO)
public class UsuarioEntidad {
    @Id
    private UUID identificador;

    @Column(length = 100, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String clave;

    @Column(name = ESTA_ACTIVO, nullable = false)
    private boolean estaActivo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = USUARIO, nullable = false)
    private UsuarioTipoUsuarioEntidad rol;
}