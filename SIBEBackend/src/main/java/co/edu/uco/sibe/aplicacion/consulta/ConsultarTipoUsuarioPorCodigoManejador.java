package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarTipoUsuarioPorCodigoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarTipoUsuarioPorCodigoManejador implements ManejadorComandoRespuesta<String, TipoUsuario> {
    private final ConsultarTipoUsuarioPorCodigoUseCase consultarTipoUsuarioPorCodigoUseCase;

    @Override
    public TipoUsuario ejecutar(String comando) {
        return this.consultarTipoUsuarioPorCodigoUseCase.ejecutar(comando);
    }
}