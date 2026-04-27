package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.modelo.Miembro;
import co.edu.uco.sibe.dominio.puerto.consulta.MiembroRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.*;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.MiembroEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.MiembroMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class MiembroRepositorioConsultaImplementacion implements MiembroRepositorioConsulta {
    private final MiembroDAO miembroDAO;
    private final InternoDAO internoDAO;
    private final MiembroMapeador miembroMapeador;

    @Override
    public Miembro consultarPorIdentificador(UUID identificador) {
        var entidad = miembroDAO.findById(identificador).orElse(null);

        if (esNulo(entidad)) {
            return null;
        }

        return miembroMapeador.construirModelo(entidad);
    }

    @Override
    public Miembro consultarPorIdentificacion(String identificacion) {
        var entidad = miembroDAO.findFirstByNumeroIdentificacionAndEstaActivoTrue(identificacion).orElse(null);

        if (esNulo(entidad)) {
            return null;
        }

        return miembroMapeador.construirModelo(entidad);
    }

    @Override
    public MiembroDTO consultarPorIdentificacionDTO(String identificacion) {
        MiembroEntidad miembro = miembroDAO.findFirstByNumeroIdentificacionAndEstaActivoTrue(identificacion)
                .orElse(null);

        if (esNulo(miembro)) {
            return null;
        }

        return miembroMapeador.construirDTO(miembro);
    }

    @Override
    public MiembroDTO consultarPorIdCarnetDTO(String idCarnet) {
        MiembroEntidad miembro = internoDAO.findFirstByIdCarnetAndEstaActivoTrue(idCarnet)
                .orElse(null);

        if (esNulo(miembro)) {
            return null;
        }

        return miembroMapeador.construirDTO(miembro);
    }
}