package co.edu.uco.sibe.infraestructura.configuracion.bean;

import co.edu.uco.sibe.dominio.puerto.comando.*;
import co.edu.uco.sibe.dominio.puerto.consulta.*;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.puerto.servicio.EnviarCorreoElectronicoService;
import co.edu.uco.sibe.dominio.service.*;
import co.edu.uco.sibe.dominio.usecase.comando.*;
import co.edu.uco.sibe.dominio.usecase.consulta.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBean {
    @Bean
    public GuardarAccionUseCase agregarNuevaAccionUseCase(AccionRepositorioComando accionRepositorioComando,
            AccionRepositorioConsulta accionRepositorioConsulta) {
        return new GuardarAccionUseCase(accionRepositorioComando, accionRepositorioConsulta);
    }

    @Bean
    public GuardarProyectoUseCase agregarNuevoProyectoUseCase(ProyectoRepositorioComando proyectoRepositorioComando,
            ProyectoRepositorioConsulta proyectoRepositorioConsulta) {
        return new GuardarProyectoUseCase(proyectoRepositorioComando, proyectoRepositorioConsulta);
    }

    @Bean
    public GuardarUsuarioUseCase agregarNuevoUsuarioUseCase(PersonaRepositorioComando personaRepositorioComando,
            PersonaRepositorioConsulta personaRepositorioConsulta, EncriptarClaveServicio encriptarClaveServicio,
            VincularUsuarioConAreaService vincularUsuarioConAreaService,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio,
            ModificarVinculacionUsuarioConAreaService modificarVinculacionUsuarioConAreaService) {
        return new GuardarUsuarioUseCase(personaRepositorioComando, personaRepositorioConsulta, encriptarClaveServicio,
                vincularUsuarioConAreaService, autorizacionServicio, modificarVinculacionUsuarioConAreaService);
    }

    @Bean
    public EliminarUsuarioUseCase eliminarUsuarioUseCase(PersonaRepositorioComando personaRepositorioComando,
            PersonaRepositorioConsulta personaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new EliminarUsuarioUseCase(personaRepositorioComando, personaRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ModificarUsuarioUseCase modificarUsuarioUseCase(PersonaRepositorioComando personaRepositorioComando,
            PersonaRepositorioConsulta personaRepositorioConsulta,
            ModificarVinculacionUsuarioConAreaService modificarVinculacionUsuarioConAreaServicea,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ModificarUsuarioUseCase(personaRepositorioComando, personaRepositorioConsulta,
                modificarVinculacionUsuarioConAreaServicea, autorizacionServicio);
    }

    @Bean
    public ModificarContrasenaUseCase modificarContrasenaUseCase(PersonaRepositorioComando personaRepositorioComando,
            PersonaRepositorioConsulta personaRepositorioConsulta) {
        return new ModificarContrasenaUseCase(personaRepositorioComando, personaRepositorioConsulta);
    }

    @Bean
    public LoginUseCase loginUseCase(PersonaRepositorioConsulta personaRepositorioConsulta,
            EncriptarClaveServicio encryptTextService) {
        return new LoginUseCase(personaRepositorioConsulta, encryptTextService);
    }

    @Bean
    public ConsultarUsuarioPorIdentificadorUseCase consultarUsuarioPorIdentificadorUseCase(
            PersonaRepositorioConsulta personaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarUsuarioPorIdentificadorUseCase(personaRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarUsuarioPorCorreoUseCase consultarUsuarioPorCorreoUseCase(
            PersonaRepositorioConsulta personaRepositorioConsulta) {
        return new ConsultarUsuarioPorCorreoUseCase(personaRepositorioConsulta);
    }

    @Bean
    public ConsultarUsuariosUseCase consultarUsuariosUseCase(PersonaRepositorioConsulta personaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio,
            AreaRepositorioConsulta areaRepositorioConsulta) {
        return new ConsultarUsuariosUseCase(personaRepositorioConsulta, autorizacionServicio, areaRepositorioConsulta);
    }

    @Bean
    public ConsultarDireccionPorNombreUseCase consultarDireccionPorNombreUseCase(
            DireccionRepositorioConsulta direccionRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarDireccionPorNombreUseCase(direccionRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarTipoIdentificacionPorSiglaUseCase consultarTipoIdentificacionPorSiglaUseCase(
            TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta) {
        return new ConsultarTipoIdentificacionPorSiglaUseCase(tipoIdentificacionRepositorioConsulta);
    }

    @Bean
    public ConsultarTipoUsuarioPorCodigoUseCase consultarTipoUsuarioPorCodigoUseCase(
            TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta) {
        return new ConsultarTipoUsuarioPorCodigoUseCase(tipoUsuarioRepositorioConsulta);
    }

    @Bean
    public GuardarTipoUsuarioUseCase guardarTipoUsuarioUseCase(
            TipoUsuarioRepositorioComando tipoUsuarioRepositorioComando,
            TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta) {
        return new GuardarTipoUsuarioUseCase(tipoUsuarioRepositorioComando, tipoUsuarioRepositorioConsulta);
    }

    @Bean
    public GuardarTipoIdentificacionUseCase guardarTipoIdentificacionUseCase(
            TipoIdentificacionRepositorioComando tipoIdentificacionRepositorioComando,
            TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta) {
        return new GuardarTipoIdentificacionUseCase(tipoIdentificacionRepositorioComando,
                tipoIdentificacionRepositorioConsulta);
    }

    @Bean
    public GuardarSubareaUseCase guardarSubareaUseCase(SubareaRepositorioComando subareaRepositorioComando,
            SubareaRepositorioConsulta subareaRepositorioConsulta) {
        return new GuardarSubareaUseCase(subareaRepositorioComando, subareaRepositorioConsulta);
    }

    @Bean
    public GuardarAreaUseCase guardarAreaUseCase(AreaRepositorioComando areaRepositorioComando,
            AreaRepositorioConsulta areaRepositorioConsulta) {
        return new GuardarAreaUseCase(areaRepositorioComando, areaRepositorioConsulta);
    }

    @Bean
    public GuardarDireccionUseCase guardarDireccionUseCase(DireccionRepositorioComando direccionRepositorioComando,
            DireccionRepositorioConsulta direccionRepositorioConsulta) {
        return new GuardarDireccionUseCase(direccionRepositorioComando, direccionRepositorioConsulta);
    }

    @Bean
    public GuardarEstadoActividadUseCase guardarEstadoActividadUseCase(
            EstadoActividadRepositorioComando estadoActividadRepositorioComando,
            EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta) {
        return new GuardarEstadoActividadUseCase(estadoActividadRepositorioComando, estadoActividadRepositorioConsulta);
    }

    @Bean
    public GuardarTemporalidadUseCase guardarTemporalidadUseCase(
            TemporalidadRepositorioComando temporalidadRepositorioComando,
            TemporalidadRepositorioConsulta temporalidadRepositorioConsulta) {
        return new GuardarTemporalidadUseCase(temporalidadRepositorioComando, temporalidadRepositorioConsulta);
    }

    @Bean
    public SolicitarCodigoUseCase solicitarCodigoUseCase(PersonaRepositorioConsulta personaRepositorioConsulta,
            PersonaRepositorioComando personaRepositorioComando, EncriptarClaveServicio encriptarClaveServicio,
            EnviarCorreoElectronicoService enviarCorreoElectronicoService) {
        return new SolicitarCodigoUseCase(personaRepositorioConsulta, personaRepositorioComando, encriptarClaveServicio,
                enviarCorreoElectronicoService);
    }

    @Bean
    public ValidarCodigoRecuperacionClaveUseCase validarCodigoRecuperacionClaveUseCase(
            PersonaRepositorioConsulta personaRepositorioConsulta, PersonaRepositorioComando personaRepositorioComando,
            EncriptarClaveServicio encriptarClaveServicio) {
        return new ValidarCodigoRecuperacionClaveUseCase(personaRepositorioConsulta, personaRepositorioComando,
                encriptarClaveServicio);
    }

    @Bean
    public RecuperarClaveUseCase recuperarClaveUseCase(PersonaRepositorioComando personaRepositorioComando,
            PersonaRepositorioConsulta personaRepositorioConsulta, EncriptarClaveServicio encriptarClaveServicio) {
        return new RecuperarClaveUseCase(personaRepositorioComando, personaRepositorioConsulta, encriptarClaveServicio);
    }

    @Bean
    public ModificarClaveUseCase modificarClaveUseCase(PersonaRepositorioComando personaRepositorioComando,
            PersonaRepositorioConsulta personaRepositorioConsulta, EncriptarClaveServicio encriptarClaveServicio,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ModificarClaveUseCase(personaRepositorioComando, personaRepositorioConsulta, encriptarClaveServicio,
                autorizacionServicio);
    }

    @Bean
    public CargarMasivamenteEmpleadosUseCase cargarMasivamenteEmpleadosUseCase(
            EmpleadoRepositorioComando empleadoRepositorioComando,
            EmpleadoRepositorioConsulta empleadoRepositorioConsulta,
            MiembroRepositorioComando miembroRepositorioComando) {
        return new CargarMasivamenteEmpleadosUseCase(empleadoRepositorioComando, empleadoRepositorioConsulta, miembroRepositorioComando);
    }

    @Bean
    public CargarMasivamenteEstudiantesUseCase cargarMasivamenteEstudiantesUseCase(
            EstudianteRepositorioComando estudianteRepositorioComando,
            EstudianteRepositorioConsulta estudianteRepositorioConsulta,
            MiembroRepositorioComando miembroRepositorioComando) {
        return new CargarMasivamenteEstudiantesUseCase(estudianteRepositorioComando, estudianteRepositorioConsulta, miembroRepositorioComando);
    }

    @Bean
    public ModificarAccionUseCase modificarAccionUseCase(AccionRepositorioComando accionRepositorioComando,
            AccionRepositorioConsulta accionRepositorioConsulta) {
        return new ModificarAccionUseCase(accionRepositorioComando, accionRepositorioConsulta);
    }

    @Bean
    public ModificarProyectoUseCase modificarProyectoUseCase(ProyectoRepositorioComando proyectoRepositorioComando,
            ProyectoRepositorioConsulta proyectoRepositorioConsulta) {
        return new ModificarProyectoUseCase(proyectoRepositorioComando, proyectoRepositorioConsulta);
    }

    @Bean
    public GuardarIndicadorUseCase agregarNuevoIndicadorUseCase(IndicadorRepositorioComando indicadorRepositorioComando,
            IndicadorRepositorioConsulta indicadorRepositorioConsulta) {
        return new GuardarIndicadorUseCase(indicadorRepositorioComando, indicadorRepositorioConsulta);
    }

    @Bean
    public ModificarIndicadorUseCase modificarIndicadorUseCase(IndicadorRepositorioComando indicadorRepositorioComando,
            IndicadorRepositorioConsulta indicadorRepositorioConsulta) {
        return new ModificarIndicadorUseCase(indicadorRepositorioComando, indicadorRepositorioConsulta);
    }

    @Bean
    public GuardarPublicoInteresUseCase guardarPublicoInteresUseCase(
            PublicoInteresRepositorioComando publicoInteresRepositorioComando,
            PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta) {
        return new GuardarPublicoInteresUseCase(publicoInteresRepositorioComando, publicoInteresRepositorioConsulta);
    }

    @Bean
    public GuardarTipoIndicadorUseCase guardarTipoIndicadorUseCase(
            TipoIndicadorRepositorioComando tipoIndicadorRepositorioComando,
            TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta) {
        return new GuardarTipoIndicadorUseCase(tipoIndicadorRepositorioComando, tipoIndicadorRepositorioConsulta);
    }

    @Bean
    public GuardarActividadUseCase guardarActividadUseCase(ActividadRepositorioComando actividadRepositorioComando,
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            VincularActividadConAreaService vincularActividadConAreaService,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new GuardarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                vincularActividadConAreaService, autorizacionServicio);
    }

    @Bean
    public ModificarActividadUseCase modificarActividadUseCase(ActividadRepositorioComando actividadRepositorioComando,
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            ModificarVinculacionActividadConAreaService modificarVinculacionActividadConAreaService,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ModificarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                modificarVinculacionActividadConAreaService, autorizacionServicio);
    }

    @Bean
    public ConsultarActividadesPorAreaUseCase consultarActividadesPorAreaUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta, AreaRepositorioConsulta areaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarActividadesPorAreaUseCase(actividadRepositorioConsulta, areaRepositorioConsulta,
                autorizacionServicio);
    }

    @Bean
    public ConsultarActividadesPorDireccionUseCase consultarActividadesPorDireccionUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            DireccionRepositorioConsulta direccionRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarActividadesPorDireccionUseCase(actividadRepositorioConsulta, direccionRepositorioConsulta,
                autorizacionServicio);
    }

    @Bean
    public ConsultarActividadesPorSubareaUseCase consultarActividadesPorSubareaUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            SubareaRepositorioConsulta subareaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarActividadesPorSubareaUseCase(actividadRepositorioConsulta, subareaRepositorioConsulta,
                autorizacionServicio);
    }

    @Bean
    public ConsultarEjecucionesPorActividadUseCase consultarEjecucionesPorActividadUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarEjecucionesPorActividadUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarMiembroPorIdentificacionUseCase consultarMiembroPorIdentificacionUseCase(
            MiembroRepositorioConsulta miembroRepositorioConsulta) {
        return new ConsultarMiembroPorIdentificacionUseCase(miembroRepositorioConsulta);
    }

    @Bean
    public ConsultarMiembroPorIdCarnetUseCase consultarMiembroPorIdCarnetUseCase(
            MiembroRepositorioConsulta miembroRepositorioConsulta) {
        return new ConsultarMiembroPorIdCarnetUseCase(miembroRepositorioConsulta);
    }

    @Bean
    public IniciarActividadUseCase iniciarActividadUseCase(ActividadRepositorioComando actividadRepositorioComando,
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new IniciarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                estadoActividadRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public CancelarActividadUseCase cancelarActividadUseCase(ActividadRepositorioComando actividadRepositorioComando,
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new CancelarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                estadoActividadRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public FinalizarActividadUseCase finalizarActividadUseCase(ActividadRepositorioComando actividadRepositorioComando,
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta,
            RegistrarParticipanteService registrarParticipanteService,
            RegistroAsistenciaRepositorioComando registroAsistenciaRepositorioComando,
            RegistroAsistenciaRepositorioConsulta registroAsistenciaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new FinalizarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                estadoActividadRepositorioConsulta, registrarParticipanteService, registroAsistenciaRepositorioComando,
                registroAsistenciaRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarDireccionDetalladaUseCase consultarDireccionDetalladaUseCase(
            DireccionRepositorioConsulta direccionRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarDireccionDetalladaUseCase(direccionRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarAreaDetalladaUseCase consultarAreaDetalladaUseCase(
            AreaRepositorioConsulta areaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarAreaDetalladaUseCase(areaRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarSubareaDetalladaUseCase consultarSubareaDetalladaUseCase(
            SubareaRepositorioConsulta subareaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarSubareaDetalladaUseCase(subareaRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarAreaPorNombreDTOUseCase consultarAreaPorNombreDTOUseCase(
            AreaRepositorioConsulta areaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarAreaPorNombreDTOUseCase(areaRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarDireccionPorNombreDTOUseCase consultarDireccionPorNombreDTOUseCase(
            DireccionRepositorioConsulta direccionRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarDireccionPorNombreDTOUseCase(direccionRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarSubareaPorNombreDTOUseCase consultarSubareaPorNombreDTOUseCase(
            SubareaRepositorioConsulta subareaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarSubareaPorNombreDTOUseCase(subareaRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarParticipantesPorEjecucionActividadUseCase consultarParticipantesPorEjecucionActividadUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarParticipantesPorEjecucionActividadUseCase(actividadRepositorioConsulta,
                autorizacionServicio);
    }

    @Bean
    public ConsultarMesesEjecucionesFinalizadasUseCase consultarMesesEjecucionesFinalizadasUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta) {
        return new ConsultarMesesEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Bean
    public ConsultarAnnosEjecucionesFinalizadasUseCase consultarAnnosEjecucionesFinalizadasUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta) {
        return new ConsultarAnnosEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Bean
    public ConsultarSemestresActividadesEnEjecucionesFinalizadasUseCase consultarSemestresEstudiantesEnEjecucionesFinalizadasUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta) {
        return new ConsultarSemestresActividadesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Bean
    public ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase consultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta) {
        return new ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Bean
    public ConsultarTiposParticipantesEnEjecucionesFinalizadasUseCase consultarTiposParticipantesEnEjecucionesFinalizadasUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta) {
        return new ConsultarTiposParticipantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Bean
    public ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta) {
        return new ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Bean
    public ConsultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasUseCase consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta) {
        return new ConsultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Bean
    public ConsultarIndicadoresEnEjecucionesFinalizadasUseCase consultarIndicadoresEnEjecucionesFinalizadasUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta) {
        return new ConsultarIndicadoresEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Bean
    public ContarParticipantesTotalesUseCase contarParticipantesTotalesUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ContarParticipantesTotalesUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ContarAsistenciasTotalesUseCase contarAsistenciasTotalesUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ContarAsistenciasTotalesUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ContarEjecucionesTotalesUseCase contarEjecucionesTotalesUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ContarEjecucionesTotalesUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarEstadisticasParticipantesPorEstructuraUseCase consultarEstadisticasParticipantesPorEstructuraUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarEstadisticasParticipantesPorEstructuraUseCase(actividadRepositorioConsulta,
                autorizacionServicio);
    }

    @Bean
    public ConsultarEstadisticasParticipantesPorMesUseCase consultarEstadisticasParticipantesPorMesUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ConsultarEstadisticasParticipantesPorMesUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }

    @Bean
    public ConsultarIndicadoresParaActividadesUseCase consultarIndicadoresParaActividadesUseCase(
            IndicadorRepositorioConsulta indicadorRepositorioConsulta) {
        return new ConsultarIndicadoresParaActividadesUseCase(indicadorRepositorioConsulta);
    }

    @Bean
    public ContarPoblacionTotalUseCase contarPoblacionTotalUseCase(
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        return new ContarPoblacionTotalUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }
}