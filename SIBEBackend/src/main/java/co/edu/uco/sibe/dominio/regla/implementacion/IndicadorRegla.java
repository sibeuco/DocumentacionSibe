package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoAlfanumericoValido;

public final class IndicadorRegla implements Regla<Indicador> {
    private static final IndicadorRegla INSTANCIA = new IndicadorRegla();

    private IndicadorRegla() {
        super();
    }

    public static IndicadorRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_INDICADOR_NULO);
    }

    @Override
    public void validarCampos(Indicador modelo) {
        validarNombre(modelo.getNombre());
    }

    private void validarNombre(String nombre) {
        validarObligatorio(nombre, NOMBRE_INDICADOR_OBLIGATORIO);
        validarTextoAlfanumericoValido(nombre, NOMBRE_INDICADOR_INVALIDO);
        validarNumeroEntre(nombre.length(), 5, 100, LONGITUD_NOMBRE_INDICADOR_INVALIDA);
    }
}