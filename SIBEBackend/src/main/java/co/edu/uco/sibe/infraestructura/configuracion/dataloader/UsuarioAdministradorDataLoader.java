package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.fabrica.PersonaFabrica;
import co.edu.uco.sibe.aplicacion.comando.fabrica.UsuarioFabrica;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarTipoIdentificacionPorSiglaManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarTipoUsuarioPorCodigoManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosUsuarioManejador;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.service.VincularUsuarioConAreaService;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import co.edu.uco.sibe.infraestructura.adaptador.dao.DireccionDAO;
import co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosUsuarioFabrica;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.DIEZ;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_DIRECCION;

@Component
@Order(DIEZ)
@RequiredArgsConstructor
public class UsuarioAdministradorDataLoader extends DataLoader {
    private final HayDatosUsuarioManejador hayDatosUsuarioManejador;
    private final ConsultarTipoUsuarioPorCodigoManejador consultarTipoUsuarioPorCodigoManejador;
    private final ConsultarTipoIdentificacionPorSiglaManejador consultarTipoIdentificacionPorSiglaManejador;
    private final DireccionDAO direccionDAO;
    private final UsuarioFabrica usuarioFabrica;
    private final PersonaFabrica personaFabrica;
    private final PersonaRepositorioComando personaRepositorioComando;
    private final EncriptarClaveServicio encriptarClaveServicio;
    private final VincularUsuarioConAreaService vincularUsuarioConAreaService;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosUsuarioManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        var tipoUsuario = consultarTipoUsuarioPorCodigoManejador.ejecutar(ADMINISTRADOR_DIRECCION);
        var tipoIdentificacion = consultarTipoIdentificacionPorSiglaManejador.ejecutar(SIGLA_CC);
        var direccionEntidad = direccionDAO.findByNombre(NOMBRE_DIRECCION_BIENESTAR_EVANGELIZACION);

        var comando = DatosUsuarioFabrica.crearAdministradorUCO(
                tipoIdentificacion.getIdentificador(),
                tipoUsuario.getIdentificador(),
                direccionEntidad.getIdentificador()
        );

        var usuario = this.usuarioFabrica.construir(comando);
        var persona = this.personaFabrica.construir(comando);
        var area = UtilUUID.textoAUUID(comando.getArea().getArea());
        var tipoArea = TipoArea.valueOf(comando.getArea().getTipoArea());

        var claveEncriptada = this.encriptarClaveServicio.ejecutar(usuario.getClave());
        var identificador = this.personaRepositorioComando.agregarNuevoUsuario(usuario, persona, claveEncriptada);
        this.vincularUsuarioConAreaService.ejecutar(identificador, area, tipoArea);
    }
}