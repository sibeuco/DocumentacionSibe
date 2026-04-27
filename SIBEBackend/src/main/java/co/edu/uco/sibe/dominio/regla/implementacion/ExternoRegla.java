package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Externo;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarNumeroIdentificacionValido;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public class ExternoRegla implements Regla<Externo> {
    private static final ExternoRegla INSTANCIA = new ExternoRegla();

    private ExternoRegla() {
        super();
    }

    public static ExternoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_EXTERNO_NULO);
    }

    @Override
    public void validarCampos(Externo modelo) {
        validarNombreCompleto(modelo.getNombreCompleto());
        validarNumeroIdentificacion(modelo.getNumeroIdentificacion());
    }

    private void validarNombreCompleto(String nombreCompleto) {
        validarObligatorio(nombreCompleto, NOMBRE_COMPLETO_MIEMBRO_OBLIGATORIO);
        validarTextoValido(nombreCompleto, NOMBRE_COMPLETO_MIEMBRO_INVALIDO);
        validarNumeroEntre(nombreCompleto.length(), 5, 100, LONGITUD_NOMBRE_COMPLETO_MIEMBRO_INVALIDA);
    }

    private void validarNumeroIdentificacion(String numeroIdentificacion) {
        validarObligatorio(numeroIdentificacion, NUMERO_IDENTIFICACION_MIEMBRO_OBLIGATORIO);
        validarNumeroIdentificacionValido(numeroIdentificacion, NUMERO_IDENTIFICACION_MIEMBRO_INVALIDO);
        validarNumeroEntre(numeroIdentificacion.length(), 6, 12, LONGITUD_NUMERO_IDENTIFICACION_MIEMBRO_INVALIDA);
    }
}