package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarTipoIdentificacionPorSiglaUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarTipoIdentificacionPorSiglaManejador implements ManejadorComandoRespuesta<String, TipoIdentificacion> {
    private final ConsultarTipoIdentificacionPorSiglaUseCase consultarTipoIdentificacionPorSiglaUseCase;

    @Override
    public TipoIdentificacion ejecutar(String comando) {
        return this.consultarTipoIdentificacionPorSiglaUseCase.ejecutar(comando);
    }
}