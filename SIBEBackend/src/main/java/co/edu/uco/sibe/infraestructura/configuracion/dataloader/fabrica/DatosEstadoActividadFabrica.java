package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;

public final class DatosEstadoActividadFabrica {
    private DatosEstadoActividadFabrica() {
        super();
    }

    public static List<String> obtenerEstadosActividad() {
        return List.of(
                PENDIENTE,
                EN_CURSO,
                FINALIZADA
        );
    }
}