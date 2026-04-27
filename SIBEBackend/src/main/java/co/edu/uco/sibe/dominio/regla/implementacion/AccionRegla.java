package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoAlfanumericoValido;

public final class AccionRegla implements Regla<Accion> {
    private static final AccionRegla INSTANCIA = new AccionRegla();

    private AccionRegla() {
        super();
    }

    public static AccionRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_ACCION_NULO);
    }

    @Override
    public void validarCampos(Accion modelo) {
        validarDetalle(modelo.getDetalle());
        validarObjetivo(modelo.getObjetivo());
    }

    private void validarDetalle(String detalle) {
        validarObligatorio(detalle, DETALLE_ACCION_OBLIGATORIO);
        validarTextoAlfanumericoValido(detalle, DETALLE_ACCION_INVALIDO);
        validarNumeroEntre(detalle.length(), 10, 500, LONGITUD_DETALLE_ACCION_INVALIDA);
    }

    private void validarObjetivo(String objetivo) {
        validarObligatorio(objetivo, OBJETIVO_ACCION_OBLIGATORIO);
        validarTextoAlfanumericoValido(objetivo, OBJETIVO_ACCION_INVALIDO);
        validarNumeroEntre(objetivo.length(), 10, 500, LONGITUD_OBJETIVO_ACCION_INVALIDA);
    }
}