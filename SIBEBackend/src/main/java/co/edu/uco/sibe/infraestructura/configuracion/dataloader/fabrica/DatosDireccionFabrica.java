package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import co.edu.uco.sibe.aplicacion.comando.DireccionBaseComando;
import co.edu.uco.sibe.dominio.modelo.Area;
import java.util.List;

import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.NOMBRE_DIRECCION_BIENESTAR_EVANGELIZACION;

public final class DatosDireccionFabrica {

    private DatosDireccionFabrica() {
    }

    public static DireccionBaseComando crearDireccionBienestar(List<Area> areas) {
        return new DireccionBaseComando(
                NOMBRE_DIRECCION_BIENESTAR_EVANGELIZACION,
                areas
        );
    }
}