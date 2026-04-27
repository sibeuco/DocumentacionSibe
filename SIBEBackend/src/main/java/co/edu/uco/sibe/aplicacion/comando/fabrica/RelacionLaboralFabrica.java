package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.dominio.puerto.consulta.RelacionLaboralRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class RelacionLaboralFabrica {
    private final RelacionLaboralRepositorioConsulta relacionLaboralRepositorioConsulta;

    public RelacionLaboral construir(String codigo, String descripcion) {
        if (esNulo(relacionLaboralRepositorioConsulta.consultarPorCodigo(codigo))) {
            return RelacionLaboral.construir(
                    generar(uuid -> !esNulo(relacionLaboralRepositorioConsulta.consultarPorIdentificador(uuid))),
                    codigo,
                    descripcion
            );
        }

        return relacionLaboralRepositorioConsulta.consultarPorCodigo(codigo);
    }
}