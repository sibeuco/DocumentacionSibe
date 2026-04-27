package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ProyectoModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ProyectoFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoParametroRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarProyectoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ModificarProyectoManejador implements ManejadorComandoParametroRespuesta<ProyectoModificacionComando, UUID, ComandoRespuesta<UUID>> {
    private final ProyectoFabrica proyectoFabrica;
    private final ModificarProyectoUseCase modificarProyectoUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(ProyectoModificacionComando comando, UUID parametro) {
        var proyecto = this.proyectoFabrica.construirActualizar(comando, parametro);

        return new ComandoRespuesta<>(
               this.modificarProyectoUseCase.ejecutar(
                        proyecto, parametro
               )
        );
    }
}