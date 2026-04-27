package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;

public final class DatosSubareaFabrica {
    private DatosSubareaFabrica() {
        super();
    }

    public static List<String> obtenerSubareas() {
        return List.of(
                NOMBRE_SUB_AREA_DEPORTERS,
                NOMBRE_SUB_AREA_CANCHA,
                NOMBRE_SUB_AREA_EXTENSION,
                NOMBRE_SUB_AREA_BANDA,
                NOMBRE_SUB_AREA_GIMNASIO,
                NOMBRE_SUB_AREA_UNIDAD_SALUD,
                NOMBRE_SUB_AREA_ACOMPANAMIENTO,
                NOMBRE_SUB_AREA_TRABAJO_SOCIAL
        );
    }
}