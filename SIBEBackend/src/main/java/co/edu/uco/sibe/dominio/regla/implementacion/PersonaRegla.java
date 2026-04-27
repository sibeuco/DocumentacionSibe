package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.regla.Regla;
import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarCorreoValido;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

@Getter
public final class PersonaRegla implements Regla<Persona> {
    private static final PersonaRegla INSTANCIA = new PersonaRegla();

    private PersonaRegla() {
        super();
    }

    public static PersonaRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_PERSONA_NULO);
    }

    @Override
    public void validarCampos(Persona modelo) {
        validarNombres(modelo.getNombres());
        validarApellidos(modelo.getApellidos());
        validarCorreo(modelo.getCorreo());
    }

    private void validarNombres(String nombres) {
        validarObligatorio(nombres, PRIMER_NOMBRE_PERSONA_VACIO);
        validarTextoValido(nombres, PATRON_NOMBRE_PERSONA_INVALIDO);
        validarNumeroEntre(nombres.length(), 1, 50, LONGITUD_NOMBRE_PERSONA_INVALIDA);
    }

    private void validarApellidos(String apellidos) {
        validarObligatorio(apellidos, PRIMER_APELLIDO_PERSONA_VACIO);
        validarTextoValido(apellidos, PATRON_APELLIDO_PERSONA_INVALIDO);
        validarNumeroEntre(apellidos.length(), 1, 50, LONGITUD_APELLIDO_PERSONA_INVALIDA);
    }

    private void validarCorreo(String correo) {
        validarObligatorio(correo, CORREO_USUARIO_VACIO);
        validarCorreoValido(correo, PATRON_CORREO_INVALIDO);
        validarNumeroEntre(correo.length(), 10, 100, LONGITUD_CORREO_PERSONA_INVALIDA);
    }
}