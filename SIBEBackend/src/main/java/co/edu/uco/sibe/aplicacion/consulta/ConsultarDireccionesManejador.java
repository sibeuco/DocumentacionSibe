package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_DIRECCION;

@Component
@AllArgsConstructor
public class ConsultarDireccionesManejador implements ManejadorRespuesta<List<DireccionDTO>> {
    private final DireccionRepositorioConsulta direccionRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    @Override
    public List<DireccionDTO> ejecutar() {
        var todas = this.direccionRepositorioConsulta.consultarDTOs();
        ContextoUsuarioAutenticado contexto = autorizacionServicio.obtenerContexto();
        if (ADMINISTRADOR_DIRECCION.equals(contexto.getRol())) {
            return todas;
        }
        UUID direccionId = contexto.getDireccionId();
        return todas.stream()
                .filter(d -> d.getIdentificador().equals(direccionId.toString()))
                .toList();
    }
}