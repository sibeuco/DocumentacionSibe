package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoUsuarioComando;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;

public final class DatosTipoUsuarioFabrica {
    private DatosTipoUsuarioFabrica() {
        super();
    }

    public static TipoUsuarioComando crearAdministradorDireccion() {
        return new TipoUsuarioComando(
                ADMINISTRADOR_DIRECCION,
                DESCRIPCION_ADMINISTRADOR_DIRECCION,
                true, true, true, true
        );
    }

    public static TipoUsuarioComando crearAdministradorArea() {
        return new TipoUsuarioComando(
                ADMINISTRADOR_AREA,
                DESCRIPCION_ADMINISTRADOR_AREA,
                true, true, true, true
        );
    }

    public static TipoUsuarioComando crearColaborador() {
        return new TipoUsuarioComando(
                COLABORADOR,
                DESCRIPCION_COLABORADOR,
                true, true, false, true
        );
    }
}