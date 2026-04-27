package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class EstadoActividadFabrica {
    private final EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;

    public EstadoActividad construir(String comando) {
        return EstadoActividad.construir(
                generar(uuid -> !esNulo(estadoActividadRepositorioConsulta.consultarPorIdentificador(uuid))),
                comando
        );
    }
}