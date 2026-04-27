package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.puerto.consulta.CiudadResidenciaRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class CiudadResidenciaFabrica {
    private final CiudadResidenciaRepositorioConsulta ciudadResidenciaRepositorioConsulta;

    public CiudadResidencia construir(String municipioResidencia) {
        if (esNulo(ciudadResidenciaRepositorioConsulta.consultarPorDescripcion(municipioResidencia))) {
            return CiudadResidencia.construir(
                    generar(uuid -> !esNulo(ciudadResidenciaRepositorioConsulta.consultarPorIdentificador(uuid))),
                    municipioResidencia
            );
        }

        return ciudadResidenciaRepositorioConsulta.consultarPorDescripcion(municipioResidencia);
    }
}