package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.regla.Regla;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarCorreoValido;

public final class UsuarioRegla implements Regla<Usuario> {
    private static final UsuarioRegla INSTANCIA = new UsuarioRegla();

    private UsuarioRegla() {
        super();
    }

    public static UsuarioRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_USUARIO_NULO);
    }

    @Override
    public void validarCampos(Usuario modelo) {
        validarCorreo(modelo.getCorreo());
    }

    private void validarCorreo(String correo) {
        validarObligatorio(correo, CORREO_USUARIO_VACIO);
        validarCorreoValido(correo, PATRON_CORREO_INVALIDO);
        validarNumeroEntre(correo.length(), 10, 100, LONGITUD_CORREO_USUARIO_INVALIDA);
    }

    public void validarClave(String clave) {
        validarObligatorio(clave, CONTRASENA_VACIA);
        if (clave.length() < 8) {
            throw new PatronExcepcion(CONTRASENA_LONGITUD_MINIMA);
        }
        if (clave.chars().noneMatch(Character::isUpperCase)) {
            throw new PatronExcepcion(CONTRASENA_REQUIERE_MAYUSCULA);
        }
        if (clave.chars().noneMatch(Character::isLowerCase)) {
            throw new PatronExcepcion(CONTRASENA_REQUIERE_MINUSCULA);
        }
        if (clave.chars().noneMatch(Character::isDigit)) {
            throw new PatronExcepcion(CONTRASENA_REQUIERE_NUMERO);
        }
        validarNumeroEntre(clave.length(), 8, 20, LONGITUD_CLAVE_USUARIO_INVALIDA);
    }
}