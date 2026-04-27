package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_EXISTE_USUARIO_CON_IDENTIFICADOR;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_SE_PUEDE_ELIMINAR_ADMIN_DIRECCION_MINIMO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_DIRECCION;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class EliminarUsuarioUseCase {

    private static final long MINIMO_ADMINISTRADORES_DIRECCION = 2;

    private final PersonaRepositorioComando personaRepositorioComando;
    private final PersonaRepositorioConsulta personaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public EliminarUsuarioUseCase(PersonaRepositorioComando personaRepositorioComando,
            PersonaRepositorioConsulta personaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.personaRepositorioComando = personaRepositorioComando;
        this.personaRepositorioConsulta = personaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public UUID ejecutar(UUID identificador) {
        autorizacionServicio.validarAccesoAUsuario(identificador);
        validarSiNoExisteUsuarioConId(identificador);
        validarMinimoAdministradoresDireccion(identificador);

        this.personaRepositorioComando.eliminarUsuario(identificador);

        return identificador;
    }

    private void validarSiNoExisteUsuarioConId(UUID identificador) {
        if (esNulo(this.personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador))) {
            throw new ValorInvalidoExcepcion(
                    obtenerMensajeConParametro(NO_EXISTE_USUARIO_CON_IDENTIFICADOR, identificador));
        }
    }

    private void validarMinimoAdministradoresDireccion(UUID identificador) {
        var persona = this.personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador);
        if (esNulo(persona)) {
            return;
        }
        var usuario = this.personaRepositorioConsulta.consultarUsuarioPorCorreo(persona.getCorreo());
        if (esNulo(usuario) || esNulo(usuario.getTipoUsuario())) {
            return;
        }
        if (ADMINISTRADOR_DIRECCION.equals(usuario.getTipoUsuario().getCodigo())) {
            long cantidadAdminsDireccion = this.personaRepositorioConsulta
                    .contarUsuariosActivosPorTipoUsuario(ADMINISTRADOR_DIRECCION);
            if (cantidadAdminsDireccion <= MINIMO_ADMINISTRADORES_DIRECCION) {
                throw new ValorInvalidoExcepcion(NO_SE_PUEDE_ELIMINAR_ADMIN_DIRECCION_MINIMO);
            }
        }
    }
}