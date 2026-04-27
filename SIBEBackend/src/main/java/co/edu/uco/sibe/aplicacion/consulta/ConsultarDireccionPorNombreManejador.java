package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarDireccionPorNombreUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarDireccionPorNombreManejador implements ManejadorComandoRespuesta<String, Direccion> {
    private final ConsultarDireccionPorNombreUseCase consultarDireccionPorNombreUseCase;

    @Override
    public Direccion ejecutar(String comando) {
        return this.consultarDireccionPorNombreUseCase.ejecutar(comando);
    }
}