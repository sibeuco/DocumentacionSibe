package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import co.edu.uco.sibe.aplicacion.comando.AreaComando;
import co.edu.uco.sibe.aplicacion.comando.UsuarioComando;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;

public final class DatosUsuarioFabrica {

    private DatosUsuarioFabrica() {
        super();
    }

    public static UsuarioComando crearAdministradorUCO(UUID tipoIdentificacion, UUID tipoUsuario, UUID direccion) {
        var area = new AreaComando(
                direccion.toString(),
                TipoArea.DIRECCION.toString()
        );

        return new UsuarioComando(
                tipoIdentificacion.toString(),
                NUMERO_ID_ADMIN,
                NOMBRE_ADMIN,
                APELLIDO_ADMIN,
                EMAIL_ADMIN,
                CLAVE_ADMIN,
                tipoUsuario.toString(),
                area
        );
    }
}