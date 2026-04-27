package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public final class TemporalidadRegla implements Regla<Temporalidad> {
    private static final TemporalidadRegla INSTANCIA = new TemporalidadRegla();

    private TemporalidadRegla() {
        super();
    }

    public static TemporalidadRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_TEMPORALIDAD_NULO);
    }

    @Override
    public void validarCampos(Temporalidad modelo) {
        validarNombre(modelo.getNombre());
    }

    private void validarNombre(String nombre) {
        validarObligatorio(nombre, NOMBRE_TEMPORALIDAD_OBLIGATORIO);
        validarTextoValido(nombre, NOMBRE_TEMPORALIDAD_INVALIDO);
        validarNumeroEntre(nombre.length(), 5, 30, LONGITUD_NOMBRE_TEMPORALIDAD_INVALIDA);
    }
}