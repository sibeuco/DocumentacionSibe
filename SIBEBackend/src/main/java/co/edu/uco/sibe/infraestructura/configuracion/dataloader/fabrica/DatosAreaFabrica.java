package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import co.edu.uco.sibe.aplicacion.comando.AreaBaseComando;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import java.util.ArrayList;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;

public final class DatosAreaFabrica {

    private DatosAreaFabrica() {
        super();
    }

    public static AreaBaseComando crearBienestar(List<Subarea> subareas) {
        return new AreaBaseComando(
                NOMBRE_AREA_BIENESTAR,
                subareas
        );
    }

    public static AreaBaseComando crearEvangelizacion() {
        return new AreaBaseComando(
                NOMBRE_AREA_EVANGELIZACION,
                new ArrayList<>()
        );
    }

    public static AreaBaseComando crearHogarJuvenil() {
        return new AreaBaseComando(
                NOMBRE_AREA_HOGAR_JUVENIL,
                new ArrayList<>()
        );
    }

    public static AreaBaseComando crearServicioAtencionUsuario() {
        return new AreaBaseComando(
                NOMBRE_AREA_SERVICIO_USUARIO,
                new ArrayList<>()
        );
    }
}