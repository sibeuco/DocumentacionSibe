package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.TipoIdentificacionDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TipoIdentificacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TipoIdentificacionMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CERO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.esNumeroMayor;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class TipoIdentificacionRepositorioConsultaImplementacion implements TipoIdentificacionRepositorioConsulta {
    private final TipoIdentificacionDAO tipoIdentificacionDAO;
    private final TipoIdentificacionMapeador tipoIdentificacionMapeador;

    @Override
    public List<TipoIdentificacionDTO> consultarDTOs() {
        var entidades = this.tipoIdentificacionDAO.findAll();

        return this.tipoIdentificacionMapeador.construirDTOs(entidades);
    }

    @Override
    public TipoIdentificacion consultarPorIdentificador(UUID identificador) {
        var entidad = this.tipoIdentificacionDAO.findById(identificador).orElse(null);

        if (esNulo(entidad)){
            return null;
        }

        return this.tipoIdentificacionMapeador.construirModelo(entidad);
    }

    @Override
    public boolean hayDatos() {
        var cantidad = tipoIdentificacionDAO.count();

        return esNumeroMayor(cantidad, CERO);
    }

    @Override
    public TipoIdentificacion consultarPorSigla(String sigla) {
        var entidad = this.tipoIdentificacionDAO.findBySigla(sigla);

        if (esNulo(entidad)){
            return null;
        }

        return this.tipoIdentificacionMapeador.construirModelo(entidad);
    }

    @Override
    public TipoIdentificacion consultarPorDescripcion(String descripcion) {
        var entidad = this.tipoIdentificacionDAO.findByDescripcion(descripcion);

        if (esNulo(entidad)){
            return null;
        }

        return this.tipoIdentificacionMapeador.construirModelo(entidad);
    }
}