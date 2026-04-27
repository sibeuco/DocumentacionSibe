package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;

public class DatosPublicoInteresFabrica {
    private DatosPublicoInteresFabrica() {
        super();
    }

    public static List<String> crearPublicosInteres() {
        return List.of(
                PUBLICO_INTERES_REGISTROS_CALIFICADOS_PROGRAMA,
                PUBLICO_INTERES_ACREDITACION_INSTITUCIONAL,
                PUBLICO_INTERES_CERTIFICACIONES_ISO,
                PUBLICO_INTERES_MINISTERIO_DE_EDUCACION,
                PUBLICO_INTERES_PLAN_PASTORAL_DIOSESANO
        );
    }
}