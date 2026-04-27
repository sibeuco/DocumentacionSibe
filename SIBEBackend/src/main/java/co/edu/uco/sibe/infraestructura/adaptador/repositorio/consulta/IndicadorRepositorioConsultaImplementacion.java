package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.puerto.consulta.IndicadorRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.constante.IndicadorConstante;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.IndicadorMapeador;
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
public class IndicadorRepositorioConsultaImplementacion implements IndicadorRepositorioConsulta {
    private final IndicadorDAO indicadorDAO;
    private final IndicadorMapeador indicadorMapeador;

    @Override
    public List<IndicadorDTO> consultarDTOs() {
        var entidades = indicadorDAO.findAll();

        return this.indicadorMapeador.construirDTOs(entidades);
    }

    @Override
    public Page<IndicadorDTO> consultarDTOsPaginado(int pagina, int tamano) {
        var entidades = indicadorDAO.findAll(PageRequest.of(pagina, tamano, Sort.by(Sort.Direction.ASC, "nombre")));

        return this.indicadorMapeador.construirDTOsPaginado(entidades);
    }

    @Override
    public List<IndicadorDTO> consultarDTOsParaActividades() {
        return consultarDTOs().stream()
                .filter(dto -> !IndicadorConstante.esIndicadorGlobal(dto.getNombre()))
                .toList();
    }

    @Override
    public Indicador consultarPorIdentificador(UUID identificador) {
        var entidad = this.indicadorDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.indicadorMapeador.construriModelo(entidad);
    }

    @Override
    public Indicador consultarPorNombre(String nombre) {
        var entidad = this.indicadorDAO.findByNombre(nombre);

        if(esNulo(entidad)) {
            return null;
        }

        return this.indicadorMapeador.construriModelo(entidad);
    }
}