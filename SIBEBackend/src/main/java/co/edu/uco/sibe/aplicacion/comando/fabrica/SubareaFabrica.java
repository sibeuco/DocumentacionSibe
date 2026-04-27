package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class SubareaFabrica {
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;

    public Subarea construir(String nombre, List<Actividad> actividades) {
        return Subarea.construir(
                generar(uuid -> !esNulo(subareaRepositorioConsulta.consultarPorIdentificador(uuid))),
                nombre,
                actividades
        );
    }
}