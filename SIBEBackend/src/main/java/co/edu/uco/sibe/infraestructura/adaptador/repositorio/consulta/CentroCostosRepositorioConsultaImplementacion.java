package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.dominio.puerto.consulta.CentroCostosRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CentroCostosDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.CentroCostosMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class CentroCostosRepositorioConsultaImplementacion implements CentroCostosRepositorioConsulta {
    private final CentroCostosDAO centroCostosDAO;
    private final CentroCostosMapeador centroCostosMapeador;

    @Override
    public CentroCostos consultarPorIdentificador(UUID identificador) {
        var entidad = this.centroCostosDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.centroCostosMapeador.construirModelo(entidad);
    }

    @Override
    public CentroCostos consultarPorCodigo(String codigo) {
        var entidad = this.centroCostosDAO.findFirstByCodigo(codigo);

        if(esNulo(entidad)) {
            return null;
        }

        return this.centroCostosMapeador.construirModelo(entidad);
    }
}