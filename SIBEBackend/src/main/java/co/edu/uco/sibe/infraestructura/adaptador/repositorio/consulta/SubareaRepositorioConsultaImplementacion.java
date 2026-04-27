package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.AreaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.DireccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.SubareaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.SubareaDetalladaMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.SubareaMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CERO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.esNumeroMayor;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class SubareaRepositorioConsultaImplementacion implements SubareaRepositorioConsulta {
    private final SubareaDAO subareaDAO;
    private final AreaDAO areaDAO;
    private final DireccionDAO direccionDAO;
    private final SubareaMapeador subareaMapeador;
    private final SubareaDetalladaMapeador subareaDetalladaMapeador;

    @Override
    public List<SubareaDTO> consultarDTOs() {
        var entidades = this.subareaDAO.findAll();

        return this.subareaMapeador.construirDTOs(entidades);
    }

    @Override
    public List<Subarea> consultarTodos() {
        var entidades = this.subareaDAO.findAll();

        return this.subareaMapeador.construirModelos(entidades);
    }

    @Override
    public Subarea consultarPorIdentificador(UUID identificador) {
        var entidad = this.subareaDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.subareaMapeador.construirModelo(entidad);
    }

    @Override
    public boolean hayDatos() {
        var cantidad = subareaDAO.count();

        return esNumeroMayor(cantidad, CERO);
    }

    @Override
    public Subarea consultarPorNombre(String nombre) {
        var entidad = this.subareaDAO.findByNombre(nombre);

        if(esNulo(entidad)) {
            return null;
        }

        return this.subareaMapeador.construirModelo(entidad);
    }

    @Override
    public Subarea consultarPorActividad(Actividad actividad) {
        var entidad = this.subareaDAO.findByActividades_Identificador(actividad.getIdentificador());

        if(esNulo(entidad)) {
            return null;
        }

        return this.subareaMapeador.construirModelo(entidad);
    }

    @Override
    public SubareaDetalladaDTO consultarDetallePorIdentificador(UUID identificador) {
        var entidad = this.subareaDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        var dto = this.subareaDetalladaMapeador.construirDTO(entidad);

        var areaEntidad = this.areaDAO.findBySubareas_Identificador(identificador);
        if (!esNulo(areaEntidad)) {
            dto.setAreaNombre(areaEntidad.getNombre());
            var direccionEntidad = this.direccionDAO.findByAreas_Identificador(areaEntidad.getIdentificador());
            if (!esNulo(direccionEntidad)) {
                dto.setDireccionNombre(direccionEntidad.getNombre());
            }
        }

        return dto;
    }

    @Override
    public SubareaDTO consultarPorNombreDTO(String nombre) {
        var entidad = this.subareaDAO.findByNombre(nombre);

        if(esNulo(entidad)) {
            return null;
        }

        return this.subareaMapeador.construirDTO(entidad);
    }
}