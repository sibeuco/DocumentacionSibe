package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class DireccionFabrica {
    private final DireccionRepositorioConsulta direccionRepositorioConsulta;

    public Direccion construir(String nombre, List<Area> areas, List<Actividad> actividades) {
        return Direccion.construir(
                generar(uuid -> !esNulo(direccionRepositorioConsulta.consultarPorIdentificador(uuid))),
                nombre,
                areas,
                actividades
        );
    }
}