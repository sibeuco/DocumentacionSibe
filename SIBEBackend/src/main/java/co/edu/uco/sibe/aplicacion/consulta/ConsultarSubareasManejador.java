package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_DIRECCION;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_AREA;

@Component
@AllArgsConstructor
public class ConsultarSubareasManejador implements ManejadorRespuesta<List<Subarea>> {
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    @Override
    public List<Subarea> ejecutar() {
        var todas = this.subareaRepositorioConsulta.consultarTodos();
        ContextoUsuarioAutenticado contexto = autorizacionServicio.obtenerContexto();
        if (ADMINISTRADOR_DIRECCION.equals(contexto.getRol())) {
            return todas;
        }
        UUID subareaId = contexto.getSubareaId();
        if (ADMINISTRADOR_AREA.equals(contexto.getRol())) {
            return todas;
        }
        return todas.stream()
                .filter(s -> s.getIdentificador().equals(subareaId))
                .toList();
    }
}