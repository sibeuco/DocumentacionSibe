package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoIdentificacionComando;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class TipoIdentificacionFabrica {
    private final TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    public TipoIdentificacion construir(TipoIdentificacionComando comando) {
        return TipoIdentificacion.construir(
                generar(uuid -> !esNulo(tipoIdentificacionRepositorioConsulta.consultarPorIdentificador(uuid))),
                comando.getSigla(),
                comando.getDescripcion()
        );
    }
}