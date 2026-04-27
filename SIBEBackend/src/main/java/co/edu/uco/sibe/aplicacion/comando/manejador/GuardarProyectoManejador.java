package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ProyectoComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ProyectoFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarProyectoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarProyectoManejador implements ManejadorComandoRespuesta<ProyectoComando, ComandoRespuesta<UUID>> {
    private final ProyectoFabrica proyectoFabrica;
    private final GuardarProyectoUseCase guardarProyectoUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(ProyectoComando comando) {
        var proyecto = this.proyectoFabrica.construir(comando);

        return new ComandoRespuesta<>(
                this.guardarProyectoUseCase.ejecutar(
                        proyecto
                )
        );
    }
}