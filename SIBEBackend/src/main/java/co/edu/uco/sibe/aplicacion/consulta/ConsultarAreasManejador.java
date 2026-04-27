package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_DIRECCION;

@Component
@AllArgsConstructor
public class ConsultarAreasManejador implements ManejadorRespuesta<List<AreaDTO>> {
    private final AreaRepositorioConsulta areaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    @Override
    public List<AreaDTO> ejecutar() {
        var todas = this.areaRepositorioConsulta.consultarDTOs();
        ContextoUsuarioAutenticado contexto = autorizacionServicio.obtenerContexto();
        if (ADMINISTRADOR_DIRECCION.equals(contexto.getRol())) {
            return todas;
        }
        UUID areaId = contexto.getAreaId();
        if (areaId == null) {
            return List.of();
        }
        UUID finalAreaId = areaId;
        return todas.stream()
                .filter(a -> a.getIdentificador().equals(finalAreaId.toString()))
                .toList();
    }
}