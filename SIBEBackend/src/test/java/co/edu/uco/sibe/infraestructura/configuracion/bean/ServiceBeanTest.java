package co.edu.uco.sibe.infraestructura.configuracion.bean;

import co.edu.uco.sibe.dominio.puerto.comando.*;
import co.edu.uco.sibe.dominio.puerto.consulta.*;
import co.edu.uco.sibe.dominio.puerto.servicio.ContextoUsuarioProveedorServicio;
import co.edu.uco.sibe.dominio.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceBeanTest {

    @Mock private UsuarioOrganizacionComando usuarioOrganizacionComando;
    @Mock private SubareaRepositorioComando subareaRepositorioComando;
    @Mock private AreaRepositorioComando areaRepositorioComando;
    @Mock private DireccionRepositorioComando direccionRepositorioComando;
    @Mock private SubareaRepositorioConsulta subareaRepositorioConsulta;
    @Mock private AreaRepositorioConsulta areaRepositorioConsulta;
    @Mock private DireccionRepositorioConsulta direccionRepositorioConsulta;
    @Mock private ParticipanteRepositorioConsulta participanteRepositorioConsulta;
    @Mock private ParticipanteRepositorioComando participanteRepositorioComando;
    @Mock private UsuarioOrganizacionRepositorioConsulta usuarioOrganizacionRepositorioConsulta;
    @Mock private ContextoUsuarioProveedorServicio contextoUsuarioProveedorServicio;
    @Mock private ActividadRepositorioConsulta actividadRepositorioConsulta;
    @Mock private VincularActividadConAreaService vincularActividadConAreaService;
    @Mock private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ServiceBean serviceBean;

    @BeforeEach
    void setUp() {
        serviceBean = new ServiceBean();
    }

    @Test
    void deberiaCrearVincularUsuarioConAreaService() {
        VincularUsuarioConAreaService service = serviceBean.vincularUsuarioConAreaService(usuarioOrganizacionComando);
        assertNotNull(service);
    }

    @Test
    void deberiaCrearModificarVinculacionUsuarioConAreaService() {
        ModificarVinculacionUsuarioConAreaService service = serviceBean.modificarVinculacionUsuarioConAreaService(usuarioOrganizacionComando);
        assertNotNull(service);
    }

    @Test
    void deberiaCrearVincularActividadConAreaService() {
        VincularActividadConAreaService service = serviceBean.vincularActividadConAreaService(
                subareaRepositorioComando, areaRepositorioComando, direccionRepositorioComando);
        assertNotNull(service);
    }

    @Test
    void deberiaCrearModificarVinculacionActividadConAreaService() {
        ModificarVinculacionActividadConAreaService service = serviceBean.modificarVinculacionActividadConAreaService(
                subareaRepositorioComando, areaRepositorioComando, direccionRepositorioComando,
                subareaRepositorioConsulta, areaRepositorioConsulta, direccionRepositorioConsulta,
                vincularActividadConAreaService);
        assertNotNull(service);
    }

    @Test
    void deberiaCrearRegistrarParticipanteService() {
        RegistrarParticipanteService service = serviceBean.registrarParticipanteService(
                participanteRepositorioConsulta, participanteRepositorioComando);
        assertNotNull(service);
    }

    @Test
    void deberiaCrearContarUsuariosPorOrganizacionService() {
        ContarUsuariosPorOrganizacionService service = serviceBean.contarUsuariosPorOrganizacionService(
                usuarioOrganizacionRepositorioConsulta, autorizacionServicio);
        assertNotNull(service);
    }

    @Test
    void deberiaCrearAutorizacionContextoOrganizacionalServicio() {
        AutorizacionContextoOrganizacionalServicio service = serviceBean.autorizacionContextoOrganizacionalServicio(
                contextoUsuarioProveedorServicio, areaRepositorioConsulta, subareaRepositorioConsulta,
                actividadRepositorioConsulta, usuarioOrganizacionRepositorioConsulta);
        assertNotNull(service);
    }
}
