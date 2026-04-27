package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.puerto.consulta.ProyectoRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ProyectoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.ProyectoMapeador;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class ProyectoRepositorioConsultaImplementacion implements ProyectoRepositorioConsulta {
    private final ProyectoDAO proyectoDAO;
    private final ProyectoMapeador proyectoMapeador;

    @Override
    public List<ProyectoDTO> consultarDTOs() {
        var entidades = this.proyectoDAO.findAllConAcciones();

        return this.proyectoMapeador.construirDTOs(entidades);
    }

    @Override
    public Page<ProyectoDTO> consultarDTOsPaginado(int pagina, int tamano) {
        var entidades = this.proyectoDAO.findAllConAcciones(PageRequest.of(pagina, tamano, Sort.by(Sort.Direction.ASC, "nombre")));

        return this.proyectoMapeador.construirDTOsPaginado(entidades);
    }

    @Override
    public Proyecto consultarPorIdentificador(UUID identificador) {
        var entidad = this.proyectoDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)){
            return null;
        }

        return this.proyectoMapeador.construriModelo(entidad);
    }

    @Override
    public Proyecto consultarPorNumeroProyecto(String numeroProyecto) {
        var entidad = this.proyectoDAO.findByNumeroProyecto(numeroProyecto);

        if(esNulo(entidad)){
            return null;
        }

        return this.proyectoMapeador.construriModelo(entidad);
    }
}