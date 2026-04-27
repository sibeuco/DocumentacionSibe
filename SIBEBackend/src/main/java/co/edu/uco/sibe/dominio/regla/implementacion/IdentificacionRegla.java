package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarNumeroIdentificacionValido;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;

public final class IdentificacionRegla implements Regla<Identificacion> {
    private static final IdentificacionRegla INSTANCIA = new IdentificacionRegla();

    private IdentificacionRegla() {
        super();
    }

    public static IdentificacionRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_IDENTIFICACION_NULO);
    }

    @Override
    public void validarCampos(Identificacion modelo) {
        validarNumeroIdentificacion(modelo.getNumeroIdentificacion());
    }

    private void validarNumeroIdentificacion(String numeroIdentificacion) {
        validarObligatorio(numeroIdentificacion, DOCUMENTO_PERSONA_VACIO);
        validarNumeroIdentificacionValido(numeroIdentificacion, PATRON_DOCUMENTO_PERSONA_INVALIDO);
        validarNumeroEntre(numeroIdentificacion.length(), 5, 12, LONGITUD_NUMERO_IDENTIFICACION__INVALIDA);
    }
}