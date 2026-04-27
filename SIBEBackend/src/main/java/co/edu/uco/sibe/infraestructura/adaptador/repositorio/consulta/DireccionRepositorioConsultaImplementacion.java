package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.DireccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.DireccionDetalladaMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.DireccionMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CERO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.esNumeroMayor;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class DireccionRepositorioConsultaImplementacion implements DireccionRepositorioConsulta {
    private final DireccionDAO direccionDAO;
    private final DireccionMapeador direccionMapeador;
    private final DireccionDetalladaMapeador direccionDetalladaMapeador;

    @Override
    public List<DireccionDTO> consultarDTOs() {
        var entidades = this.direccionDAO.findAll();

        return this.direccionMapeador.construirDTOs(entidades);
    }

    @Override
    public Direccion consultarPorIdentificador(UUID identificador) {
        var entidad = this.direccionDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.direccionMapeador.construirModelo(entidad);
    }

    @Override
    public boolean hayDatos() {
        var cantidad = direccionDAO.count();

        return esNumeroMayor(cantidad, CERO);
    }

    @Override
    public Direccion consultarPorNombre(String nombre) {
        var entidad = this.direccionDAO.findByNombre(nombre);

        if(esNulo(entidad)) {
            return null;
        }

        return this.direccionMapeador.construirModelo(entidad);
    }

    @Override
    public Direccion consultarPorActividad(Actividad actividad) {
        var entidad = this.direccionDAO.findByActividades_Identificador(actividad.getIdentificador());

        if(esNulo(entidad)) {
            return null;
        }

        return this.direccionMapeador.construirModelo(entidad);
    }

    @Override
    public DireccionDetalladaDTO consultarDetallePorIdentificador(UUID identificador) {
        var entidad = this.direccionDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.direccionDetalladaMapeador.construirDTO(entidad);
    }

    @Override
    public DireccionDTO consultarPorNombreDTO(String nombre) {
        var entidad = this.direccionDAO.findByNombre(nombre);

        if(esNulo(entidad)) {
            return null;
        }

        return this.direccionMapeador.construirDTO(entidad);
    }
}