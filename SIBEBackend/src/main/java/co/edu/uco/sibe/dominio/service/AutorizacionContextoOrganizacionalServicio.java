package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.UsuarioOrganizacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.ContextoUsuarioProveedorServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;

public class AutorizacionContextoOrganizacionalServicio {

    private final ContextoUsuarioProveedorServicio contextoProveedorServicio;
    private final AreaRepositorioConsulta areaRepositorioConsulta;
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;
    private final ActividadRepositorioConsulta actividadRepositorioConsulta;
    private final UsuarioOrganizacionRepositorioConsulta usuarioOrganizacionRepositorioConsulta;

    public AutorizacionContextoOrganizacionalServicio(
            ContextoUsuarioProveedorServicio contextoProveedorServicio,
            AreaRepositorioConsulta areaRepositorioConsulta,
            SubareaRepositorioConsulta subareaRepositorioConsulta,
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            UsuarioOrganizacionRepositorioConsulta usuarioOrganizacionRepositorioConsulta) {
        this.contextoProveedorServicio = contextoProveedorServicio;
        this.areaRepositorioConsulta = areaRepositorioConsulta;
        this.subareaRepositorioConsulta = subareaRepositorioConsulta;
        this.actividadRepositorioConsulta = actividadRepositorioConsulta;
        this.usuarioOrganizacionRepositorioConsulta = usuarioOrganizacionRepositorioConsulta;
    }

    public void validarAccesoADireccion(UUID direccionId) {
        var contexto = obtenerContexto();
        if (esAdministradorDireccion(contexto))
            return;
        throw new AuthorizationException(SIN_PERMISOS_PARA_ACCEDER_A_DIRECCION);
    }

    public void validarAccesoAArea(UUID areaId) {
        var contexto = obtenerContexto();
        if (esAdministradorDireccion(contexto))
            return;
        // Area admin puede acceder solo a su propia área (no al área padre de su subárea)
        if (esAdministradorDeAreaPropia(contexto) && areaId.equals(contexto.getAreaId()))
            return;
        // Admin de subárea NO tiene acceso al nivel de área
        if (esColaborador(contexto) && contexto.getSubareaId() == null && areaId.equals(contexto.getAreaId()))
            return;
        throw new AuthorizationException(SIN_PERMISOS_PARA_ACCEDER_A_AREA);
    }

    public void validarAccesoASubarea(UUID subareaId) {
        var contexto = obtenerContexto();
        if (esAdministradorDireccion(contexto))
            return;
        // Area admin puede acceder a cualquier subárea de su área
        if (esAdministradorDeAreaPropia(contexto)) {
            var subarea = subareaRepositorioConsulta.consultarPorIdentificador(subareaId);
            if (subarea != null) {
                var area = areaRepositorioConsulta.consultarPorIdentificador(contexto.getAreaId());
                if (area != null && area.getSubareas() != null) {
                    boolean perteneceASuArea = area.getSubareas().stream()
                            .anyMatch(s -> s.getIdentificador().equals(subareaId));
                    if (perteneceASuArea)
                        return;
                }
            }
            throw new AuthorizationException(SIN_PERMISOS_PARA_ACCEDER_A_SUBAREA);
        }
        // Admin de subárea puede acceder SOLO a su propia subárea
        if (esAdministradorDeSubareaPropia(contexto) && subareaId.equals(contexto.getSubareaId()))
            return;
        // Colaborador con subárea: solo su propia subárea
        if (esColaborador(contexto) && contexto.getSubareaId() != null && subareaId.equals(contexto.getSubareaId()))
            return;
        // Colaborador con área (sin subárea): acceso a cualquier subárea de su área
        if (esColaborador(contexto) && contexto.getSubareaId() == null && contexto.getAreaId() != null) {
            var area = areaRepositorioConsulta.consultarPorIdentificador(contexto.getAreaId());
            if (area != null && area.getSubareas() != null) {
                boolean perteneceASuArea = area.getSubareas().stream()
                        .anyMatch(s -> s.getIdentificador().equals(subareaId));
                if (perteneceASuArea)
                    return;
            }
        }
        throw new AuthorizationException(SIN_PERMISOS_PARA_ACCEDER_A_SUBAREA);
    }

    public void validarAccesoAActividad(UUID actividadId) {
        var contexto = obtenerContexto();
        if (esAdministradorDireccion(contexto))
            return;

        var actividad = actividadRepositorioConsulta.consultarPorIdentificador(actividadId);
        if (actividad == null) {
            throw new AuthorizationException(SIN_PERMISOS_ACTIVIDAD_NO_ENCONTRADA);
        }

        // Area admin: actividades de su área o de las subáreas hijas de su área
        if (esAdministradorDeAreaPropia(contexto)) {
            var areaDeActividad = areaRepositorioConsulta.consultarPorActividad(actividad);
            if (areaDeActividad != null && areaDeActividad.getIdentificador().equals(contexto.getAreaId()))
                return;
            var subareaDeActividad = subareaRepositorioConsulta.consultarPorActividad(actividad);
            if (subareaDeActividad != null) {
                var areaDelAdmin = areaRepositorioConsulta.consultarPorIdentificador(contexto.getAreaId());
                if (areaDelAdmin != null && areaDelAdmin.getSubareas() != null) {
                    boolean perteneceASuArea = areaDelAdmin.getSubareas().stream()
                            .anyMatch(s -> s.getIdentificador().equals(subareaDeActividad.getIdentificador()));
                    if (perteneceASuArea)
                        return;
                }
            }
            throw new AuthorizationException(SIN_PERMISOS_PARA_ACCEDER_A_ACTIVIDAD);
        }

        // Admin de subárea: SOLO actividades de su propia subárea
        if (esAdministradorDeSubareaPropia(contexto)) {
            var subareaDeActividad = subareaRepositorioConsulta.consultarPorActividad(actividad);
            if (subareaDeActividad != null && subareaDeActividad.getIdentificador().equals(contexto.getSubareaId()))
                return;
            throw new AuthorizationException(SIN_PERMISOS_PARA_ACCEDER_A_ACTIVIDAD);
        }

        if (esColaborador(contexto)) {
            if (actividad.getColaborador() != null && actividad.getColaborador().equals(contexto.getIdentificador()))
                return;
            // Colaborador con subárea: solo actividades de su subárea
            if (contexto.getSubareaId() != null) {
                var subareaDeActividad = subareaRepositorioConsulta.consultarPorActividad(actividad);
                if (subareaDeActividad != null && subareaDeActividad.getIdentificador().equals(contexto.getSubareaId()))
                    return;
            } else if (contexto.getAreaId() != null) {
                // Colaborador con área: actividades de su área directa
                var areaDeActividad = areaRepositorioConsulta.consultarPorActividad(actividad);
                if (areaDeActividad != null && areaDeActividad.getIdentificador().equals(contexto.getAreaId()))
                    return;
                // Colaborador con área: actividades de subáreas de su área
                var subareaDeActividad = subareaRepositorioConsulta.consultarPorActividad(actividad);
                if (subareaDeActividad != null) {
                    var areaDelColaborador = areaRepositorioConsulta.consultarPorIdentificador(contexto.getAreaId());
                    if (areaDelColaborador != null && areaDelColaborador.getSubareas() != null) {
                        boolean perteneceASuArea = areaDelColaborador.getSubareas().stream()
                                .anyMatch(s -> s.getIdentificador().equals(subareaDeActividad.getIdentificador()));
                        if (perteneceASuArea)
                            return;
                    }
                }
            }
            throw new AuthorizationException(SIN_PERMISOS_PARA_ACCEDER_A_ACTIVIDAD);
        }

        throw new AuthorizationException(SIN_PERMISOS_PARA_ACCEDER_A_ACTIVIDAD);
    }

    public void validarAccesoAEjecucionActividad(UUID ejecucionId) {
        var ejecucion = actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId);
        if (ejecucion == null) {
            throw new AuthorizationException(SIN_PERMISOS_EJECUCION_NO_ENCONTRADA);
        }
        validarAccesoAActividad(ejecucion.getActividad().getIdentificador());
    }

    public void validarAccesoAUsuario(UUID usuarioId) {
        var contexto = obtenerContexto();
        if (esAdministradorDireccion(contexto))
            return;
        if (usuarioId.equals(contexto.getIdentificador()))
            return;
        // Area admin: usuarios de su área directa o de sus subáreas hijas
        if (esAdministradorDeAreaPropia(contexto)) {
            var areaDelUsuario = usuarioOrganizacionRepositorioConsulta.consultarAreaIdPorUsuarioId(usuarioId);
            if (areaDelUsuario != null && areaDelUsuario.equals(contexto.getAreaId()))
                return;
            var subareaDelUsuario = usuarioOrganizacionRepositorioConsulta.consultarSubareaIdPorUsuarioId(usuarioId);
            if (subareaDelUsuario != null) {
                var area = areaRepositorioConsulta.consultarPorIdentificador(contexto.getAreaId());
                if (area != null && area.getSubareas() != null &&
                        area.getSubareas().stream().anyMatch(s -> s.getIdentificador().equals(subareaDelUsuario)))
                    return;
            }
        }
        // Admin de subárea: SOLO usuarios directamente en su subárea
        if (esAdministradorDeSubareaPropia(contexto)) {
            var subareaDelUsuario = usuarioOrganizacionRepositorioConsulta.consultarSubareaIdPorUsuarioId(usuarioId);
            if (subareaDelUsuario != null && subareaDelUsuario.equals(contexto.getSubareaId()))
                return;
        }
        throw new AuthorizationException(SIN_PERMISOS_PARA_ACCEDER_A_USUARIO);
    }

    public ContextoUsuarioAutenticado obtenerContexto() {
        return contextoProveedorServicio.obtenerContextoActual();
    }

    private boolean esAdministradorDireccion(ContextoUsuarioAutenticado contexto) {
        return ADMINISTRADOR_DIRECCION.equals(contexto.getRol());
    }

    private boolean esAdministradorArea(ContextoUsuarioAutenticado contexto) {
        return ADMINISTRADOR_AREA.equals(contexto.getRol());
    }

    private boolean esAdministradorDeAreaPropia(ContextoUsuarioAutenticado contexto) {
        return esAdministradorArea(contexto) && contexto.getAreaId() != null;
    }

    private boolean esAdministradorDeSubareaPropia(ContextoUsuarioAutenticado contexto) {
        return esAdministradorArea(contexto) && contexto.getAreaId() == null && contexto.getSubareaId() != null;
    }

    private boolean esColaborador(ContextoUsuarioAutenticado contexto) {
        return COLABORADOR.equals(contexto.getRol());
    }

    public UUID obtenerAreaIdEfectiva(ContextoUsuarioAutenticado contexto) {
        if (contexto.getAreaId() != null) {
            return contexto.getAreaId();
        }
        if (contexto.getSubareaId() != null) {
            var areaPadre = areaRepositorioConsulta.consultarPorSubarea(contexto.getSubareaId());
            if (areaPadre != null) {
                return areaPadre.getIdentificador();
            }
        }
        return null;
    }
}
