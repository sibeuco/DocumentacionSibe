package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoIndicadorComando;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIndicadorRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class TipoIndicadorFabrica {
    private final TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;

    public TipoIndicador construir(TipoIndicadorComando tipoIndicadorComando) {
        return TipoIndicador.construir(
                generar(uuid -> !esNulo(tipoIndicadorRepositorioConsulta.consultarPorIdentificador(uuid))),
                tipoIndicadorComando.getNaturaleza(),
                tipoIndicadorComando.getTipologia()
        );
    }
}