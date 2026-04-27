package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.IndicadorComando;
import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.puerto.consulta.*;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.textoAUUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class IndicadorFabrica {
    private final IndicadorRepositorioConsulta indicadorRepositorioConsulta;
    private final TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;
    private final TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;
    private final ProyectoRepositorioConsulta proyectoRepositorioConsulta;
    private final PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;
    private final PublicoInteresFabrica publicoInteresFabrica;

    public Indicador construir(IndicadorComando comando) {
        var publicosInteres = publicoInteresRepositorioConsulta.consultarTodosPorIdentificadores(comando.getPublicosInteres().stream().map(UtilUUID::textoAUUID).toList());

        return Indicador.construir(
                generar(uuid -> !esNulo(indicadorRepositorioConsulta.consultarPorIdentificador(uuid))),
                comando.getNombre(),
                tipoIndicadorRepositorioConsulta.consultarPorIdentificador(textoAUUID(comando.getTipoIndicador())),
                temporalidadRepositorioConsulta.consultarPorIdentificador(textoAUUID(comando.getTemporalidad())),
                proyectoRepositorioConsulta.consultarPorIdentificador(textoAUUID(comando.getProyecto())),
                publicosInteres
        );
    }

    public Indicador construirActualizar(IndicadorComando comando, UUID identificador) {
        var publicosInteres = publicoInteresRepositorioConsulta.consultarTodosPorIdentificadores(comando.getPublicosInteres().stream().map(UtilUUID::textoAUUID).toList());

        return Indicador.construir(
                identificador,
                comando.getNombre(),
                tipoIndicadorRepositorioConsulta.consultarPorIdentificador(textoAUUID(comando.getTipoIndicador())),
                temporalidadRepositorioConsulta.consultarPorIdentificador(textoAUUID(comando.getTemporalidad())),
                proyectoRepositorioConsulta.consultarPorIdentificador(textoAUUID(comando.getProyecto())),
                publicosInteres
        );
    }
}