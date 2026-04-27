package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoIdentificacionComando;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;

public final class DatosTipoIdentificacionFabrica {
    private DatosTipoIdentificacionFabrica() {
        super();
    }

    public static TipoIdentificacionComando crearCedulaCiudadania() {
        return new TipoIdentificacionComando(
                SIGLA_CC,
                DESCRIPCION_CC
        );
    }

    public static TipoIdentificacionComando crearTarjetaIdentidad() {
        return new TipoIdentificacionComando(
                SIGLA_TI,
                DESCRIPCION_TI
        );
    }

    public static TipoIdentificacionComando crearCedulaExtranjeria() {
        return new TipoIdentificacionComando(
                SIGLA_CE,
                DESCRIPCION_CE
        );
    }
}