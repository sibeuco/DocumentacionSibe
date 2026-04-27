package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.puerto.comando.IndicadorRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.IndicadorMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class IndicadorRepositorioComandoImplementacion implements IndicadorRepositorioComando {
    private final IndicadorDAO indicadorDAO;
    private final IndicadorMapeador indicadorMapeador;

    @Override
    public UUID guardar(Indicador indicador) {
        var entidad = indicadorMapeador.construirEntidad(indicador);

        return this.indicadorDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID modificar(Indicador indicador, UUID identificador) {
        var entidad = this.indicadorDAO.findById(identificador).orElse(null);

        assert !esNulo(entidad);
        this.indicadorMapeador.actualizarEntidad(entidad, indicador);

        return this.indicadorDAO.save(entidad).getIdentificador();
    }
}