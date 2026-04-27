package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.puerto.consulta.CiudadResidenciaRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.CiudadResidenciaMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class CiudadResidenciaRepositorioConsultaImplementacion implements CiudadResidenciaRepositorioConsulta {
    private final CiudadResidenciaDAO ciudadResidenciaDAO;
    private final CiudadResidenciaMapeador ciudadResidenciaMapeador;

    @Override
    public CiudadResidencia consultarPorIdentificador(UUID identificador) {
        var entidad = this.ciudadResidenciaDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.ciudadResidenciaMapeador.construirModelo(entidad);
    }

    @Override
    public CiudadResidencia consultarPorDescripcion(String descripcion) {
        var entidad = this.ciudadResidenciaDAO.findFirstByDescripcion(descripcion);

        if(esNulo(entidad)) {
            return null;
        }

        return this.ciudadResidenciaMapeador.construirModelo(entidad);
    }
}