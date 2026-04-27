package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.UsuarioComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.PersonaFabrica;
import co.edu.uco.sibe.aplicacion.comando.fabrica.UsuarioFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarUsuarioUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarUsuarioManejador implements ManejadorComandoRespuesta<UsuarioComando, ComandoRespuesta<UUID>> {
    private final UsuarioFabrica usuarioFabrica;
    private final PersonaFabrica personaFabrica;
    private final GuardarUsuarioUseCase guardarUsuarioUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(UsuarioComando comando) {
        var usuario = this.usuarioFabrica.construir(comando);
        var persona = this.personaFabrica.construir(comando);
        var area = UtilUUID.textoAUUID(comando.getArea().getArea());
        var tipoArea = TipoArea.valueOf(comando.getArea().getTipoArea());

        return new ComandoRespuesta<>(
                this.guardarUsuarioUseCase.ejecutar(
                        usuario,
                        persona,
                        area,
                        tipoArea
                )
        );
    }
}