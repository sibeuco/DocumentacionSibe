package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TipoUsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TipoUsuarioMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CERO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.esNumeroMayor;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class TipoUsuarioRepositorioConsultaImplementacion implements TipoUsuarioRepositorioConsulta {
    private final TipoUsuarioDAO tipoUsuarioDAO;
    private final TipoUsuarioMapeador tipoUsuarioMapeador;

    @Override
    public List<TipoUsuarioDTO> consultarDTOs() {
        var entidades = this.tipoUsuarioDAO.findAll();

        return this.tipoUsuarioMapeador.construirDTOs(entidades);
    }

    @Override
    public TipoUsuario consultarPorIdentificador(UUID identificador) {
        var entidad = this.tipoUsuarioDAO.findById(identificador).orElse(null);

        if (esNulo(entidad)){
            return null;
        }

        return this.tipoUsuarioMapeador.construirModelo(entidad);
    }

    @Override
    public boolean hayDatos() {
        var cantidad = tipoUsuarioDAO.count();

        return esNumeroMayor(cantidad, CERO);
    }

    @Override
    public TipoUsuario consultarPorCodigo(String codigo) {
        var entidad = this.tipoUsuarioDAO.findFirstByCodigo(codigo);

        if (esNulo(entidad)){
            return null;
        }

        return this.tipoUsuarioMapeador.construirModelo(entidad);
    }

    @Override
    public TipoUsuario consultarPorNombre(String nombre) {
        var entidad = this.tipoUsuarioDAO.findByNombre(nombre);

        if (esNulo(entidad)){
            return null;
        }

        return this.tipoUsuarioMapeador.construirModelo(entidad);
    }
}