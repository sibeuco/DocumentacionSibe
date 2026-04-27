package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class AreaFabrica {
    private final AreaRepositorioConsulta areaRepositorioConsulta;

    public Area construir(String nombre, List<Subarea> subareas, List<Actividad> actividades) {
        return Area.construir(
                generar(uuid -> !esNulo(areaRepositorioConsulta.consultarPorIdentificador(uuid))),
                nombre,
                subareas,
                actividades
        );
    }
}