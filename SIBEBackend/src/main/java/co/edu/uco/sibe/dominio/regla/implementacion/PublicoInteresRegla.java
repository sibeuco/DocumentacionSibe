package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public final class PublicoInteresRegla implements Regla<PublicoInteres> {
    private static final PublicoInteresRegla INSTANCIA = new PublicoInteresRegla();

    private PublicoInteresRegla() {
        super();
    }

    public static PublicoInteresRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_PUBLICO_INTERES_NULO);
    }

    @Override
    public void validarCampos(PublicoInteres modelo) {
        validarNombre(modelo.getNombre());
    }

    private void validarNombre(String nombre) {
        validarObligatorio(nombre, NOMBRE_PUBLICO_OBLIGATORIO);
        validarTextoValido(nombre, NOMBRE_PUBLICO_INVALIDO);
        validarNumeroEntre(nombre.length(), 5, 50, LONGITUD_NOMBRE_PUBLICO_INVALIDA);
    }
}