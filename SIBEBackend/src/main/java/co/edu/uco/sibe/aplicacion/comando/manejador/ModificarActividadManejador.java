package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ActividadModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ActividadFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoParametroRespuesta;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarActividadUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ModificarActividadManejador implements ManejadorComandoParametroRespuesta<ActividadModificacionComando, UUID, ComandoRespuesta<UUID>> {
    private final ActividadFabrica actividadFabrica;
    private final ModificarActividadUseCase modificarActividadUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(ActividadModificacionComando comando, UUID identificador) {
        var actividad = actividadFabrica.construirActualizar(comando, identificador);
        var ejecuciones = actividadFabrica.construirActualizarEjecuciones(comando.getFechasProgramada(), actividad);
        var area = UtilUUID.textoAUUID(comando.getArea().getArea());
        var tipoArea = TipoArea.valueOf(comando.getArea().getTipoArea().toUpperCase());

        return new ComandoRespuesta<>(
                modificarActividadUseCase.ejecutar(
                        actividad,
                        ejecuciones,
                        area,
                        tipoArea,
                        identificador
                )
        );
    }
}