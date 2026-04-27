package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.puerto.consulta.IdentificacionRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IdentificacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.IdentificacionMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class IdentificacionRepositorioConsultaImplementacion implements IdentificacionRepositorioConsulta {
    private final IdentificacionDAO identificacionDAO;
    private final IdentificacionMapeador identificacionMapeador;

    @Override
    public Identificacion consultarPorIdentificador(UUID identificador) {
        var entidad = identificacionDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.identificacionMapeador.construirModelo(entidad);
    }

    @Override
    public Identificacion consultarPorNumeroIdentificacion(String numeroIdentificacion) {
        var entidad = identificacionDAO.findFirstByNumeroIdentificacion(numeroIdentificacion);

        if(esNulo(entidad)) {
            return null;
        }

        return this.identificacionMapeador.construirModelo(entidad);
    }
}