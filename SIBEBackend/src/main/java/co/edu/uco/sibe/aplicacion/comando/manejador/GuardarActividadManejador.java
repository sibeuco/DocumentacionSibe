package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ActividadComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ActividadFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarActividadUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarActividadManejador implements ManejadorComandoRespuesta<ActividadComando, ComandoRespuesta<UUID>> {
    private final ActividadFabrica actividadFabrica;
    private final GuardarActividadUseCase guardarActividadUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(ActividadComando comando) {
        var actividad = actividadFabrica.construir(comando);
        var ejecuciones = actividadFabrica.construirEjecuciones(comando.getFechasProgramadas(), actividad);
        var area = UtilUUID.textoAUUID(comando.getArea().getArea());
        var tipoArea = TipoArea.valueOf(comando.getArea().getTipoArea().toUpperCase());

        return new ComandoRespuesta<>(
                guardarActividadUseCase.ejecutar(
                        actividad,
                        ejecuciones,
                        area,
                        tipoArea
                )
        );
    }
}