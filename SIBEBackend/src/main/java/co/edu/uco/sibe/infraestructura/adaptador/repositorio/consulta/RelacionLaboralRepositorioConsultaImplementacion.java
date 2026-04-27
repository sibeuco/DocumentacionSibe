package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.dominio.puerto.consulta.RelacionLaboralRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RelacionLaboralDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.RelacionLaboralMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class RelacionLaboralRepositorioConsultaImplementacion implements RelacionLaboralRepositorioConsulta {
    private final RelacionLaboralDAO relacionLaboralDAO;
    private final RelacionLaboralMapeador relacionLaboralMapeador;

    @Override
    public RelacionLaboral consultarPorIdentificador(UUID identificador) {
        var entidad = this.relacionLaboralDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.relacionLaboralMapeador.construirModelo(entidad);
    }

    @Override
    public RelacionLaboral consultarPorCodigo(String codigo) {
        var entidad = this.relacionLaboralDAO.findFirstByCodigo(codigo);

        if(esNulo(entidad)) {
            return null;
        }

        return this.relacionLaboralMapeador.construirModelo(entidad);
    }
}