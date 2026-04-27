package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.service.ModificarVinculacionUsuarioConAreaService;
import co.edu.uco.sibe.dominio.service.VincularUsuarioConAreaService;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.CORREO_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.DOCUMENTO_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class GuardarUsuarioUseCase {
    private final PersonaRepositorioComando personaRepositorioComando;
    private final PersonaRepositorioConsulta personaRepositorioConsulta;
    private final EncriptarClaveServicio encriptarClaveServicio;
    private final VincularUsuarioConAreaService vincularUsuarioConAreaService;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;
    private final ModificarVinculacionUsuarioConAreaService modificarVinculacionUsuarioConAreaService;

    public GuardarUsuarioUseCase(PersonaRepositorioComando personaRepositorioComando,
            PersonaRepositorioConsulta personaRepositorioConsulta, EncriptarClaveServicio encriptarClaveServicio,
            VincularUsuarioConAreaService vincularUsuarioConAreaService,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio,
            ModificarVinculacionUsuarioConAreaService modificarVinculacionUsuarioConAreaService) {
        this.personaRepositorioComando = personaRepositorioComando;
        this.personaRepositorioConsulta = personaRepositorioConsulta;
        this.encriptarClaveServicio = encriptarClaveServicio;
        this.vincularUsuarioConAreaService = vincularUsuarioConAreaService;
        this.autorizacionServicio = autorizacionServicio;
        this.modificarVinculacionUsuarioConAreaService = modificarVinculacionUsuarioConAreaService;
    }

    public UUID ejecutar(Usuario usuario, Persona persona, UUID area, TipoArea tipoArea) {
        autorizacionServicio.validarAccesoAArea(area);
        MotoresFabrica.MOTOR_USUARIO.ejecutar(usuario, TipoOperacion.CREAR);
        MotoresFabrica.MOTOR_IDENTIFICACION.ejecutar(persona.getIdentificacion(), TipoOperacion.CREAR);
        MotoresFabrica.MOTOR_PERSONA.ejecutar(persona, TipoOperacion.CREAR);

        validarUsuarioExisteConCorreo(usuario.getCorreo());
        validarUsuarioExisteConDocumento(persona.getIdentificacion().getNumeroIdentificacion());

        var claveEncriptada = this.encriptarClaveServicio.ejecutar(usuario.getClave());

        var usuarioInactivo = this.personaRepositorioConsulta.consultarUsuarioInactivoPorCorreo(usuario.getCorreo());
        if (!esNulo(usuarioInactivo)) {
            var personaIdentificador = this.personaRepositorioComando.reactivarUsuario(
                    usuario.getCorreo(), usuario, persona, claveEncriptada);
            this.modificarVinculacionUsuarioConAreaService.ejecutar(personaIdentificador, area, tipoArea);
            return personaIdentificador;
        }

        var identificador = this.personaRepositorioComando.agregarNuevoUsuario(usuario, persona, claveEncriptada);

        this.vincularUsuarioConAreaService.ejecutar(identificador, area, tipoArea);

        return identificador;
    }

    private void validarUsuarioExisteConCorreo(String correo) {
        if (!esNulo(this.personaRepositorioConsulta.consultarUsuarioPorCorreo(correo))) {
            throw new ValorDuplicadoExcepcion(CORREO_EXISTENTE);
        }
    }

    private void validarUsuarioExisteConDocumento(String numeroIdentificacion) {
        if (!esNulo(this.personaRepositorioConsulta.consultarPersonaPorDocumento(numeroIdentificacion))) {
            throw new ValorDuplicadoExcepcion(DOCUMENTO_EXISTENTE);
        }
    }
}