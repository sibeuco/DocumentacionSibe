package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.dominio.puerto.consulta.CentroCostosRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class CentroCostosFabrica {
    private final CentroCostosRepositorioConsulta centroCostosRepositorioConsulta;

    public CentroCostos construir(String codigo, String descripcion) {
        if(esNulo(centroCostosRepositorioConsulta.consultarPorCodigo(codigo))) {
            return CentroCostos.construir(
                    generar(uuid -> !esNulo(centroCostosRepositorioConsulta.consultarPorIdentificador(uuid))),
                    codigo,
                    descripcion
            );
        }

        return centroCostosRepositorioConsulta.consultarPorCodigo(codigo);
    }
}