package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_SE_ECONTRARON_USUARIOS;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_DIRECCION;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_AREA;

public class ConsultarUsuariosUseCase {
    private final PersonaRepositorioConsulta personaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;
    private final AreaRepositorioConsulta areaRepositorioConsulta;

    public ConsultarUsuariosUseCase(PersonaRepositorioConsulta personaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio,
            AreaRepositorioConsulta areaRepositorioConsulta) {
        this.personaRepositorioConsulta = personaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
        this.areaRepositorioConsulta = areaRepositorioConsulta;
    }

    public List<UsuarioDTO> ejecutar() {
        List<UsuarioDTO> usuarios = personaRepositorioConsulta.consultarUsuariosDTO();

        if (usuarios.isEmpty()) {
            throw new ValorInvalidoExcepcion(NO_SE_ECONTRARON_USUARIOS);
        }

        ContextoUsuarioAutenticado contexto = autorizacionServicio.obtenerContexto();
        if (ADMINISTRADOR_DIRECCION.equals(contexto.getRol())) {
            return usuarios;
        }

        if (ADMINISTRADOR_AREA.equals(contexto.getRol())) {
            if (contexto.getAreaId() != null) {
                // Area admin: usuarios de su área directa + sus subáreas hijas
                Set<String> idsPermitidos = obtenerIdsOrganizacionalesDeArea(contexto.getAreaId());
                return usuarios.stream()
                        .filter(u -> u.getArea() != null && u.getArea().getIdentificador() != null
                                && idsPermitidos.contains(u.getArea().getIdentificador()))
                        .toList();
            } else if (contexto.getSubareaId() != null) {
                // Admin de subárea: SOLO usuarios de su propia subárea
                String subareaIdStr = contexto.getSubareaId().toString();
                return usuarios.stream()
                        .filter(u -> u.getArea() != null && subareaIdStr.equals(u.getArea().getIdentificador()))
                        .toList();
            }
            return List.of();
        }

        return usuarios;
    }

    public Page<UsuarioDTO> ejecutar(String tipoUsuario, int pagina, int tamano, boolean excluir) {
        return personaRepositorioConsulta.consultarUsuariosDTOPaginado(tipoUsuario, pagina, tamano, excluir);
    }

    private Set<String> obtenerIdsOrganizacionalesDeArea(UUID areaId) {
        Set<String> ids = new java.util.HashSet<>();
        ids.add(areaId.toString());
        Area area = areaRepositorioConsulta.consultarPorIdentificador(areaId);
        if (area != null && area.getSubareas() != null) {
            area.getSubareas().stream()
                    .map(s -> s.getIdentificador().toString())
                    .forEach(ids::add);
        }
        return ids;
    }
}