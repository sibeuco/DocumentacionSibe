package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.dominio.puerto.consulta.TemporalidadRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class TemporalidadFabrica {
    private final TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;

    public Temporalidad construir(String comando) {
        return Temporalidad.construir(
                generar(uuid -> !esNulo(temporalidadRepositorioConsulta.consultarPorIdentificador(uuid))),
                comando
        );
    }
}