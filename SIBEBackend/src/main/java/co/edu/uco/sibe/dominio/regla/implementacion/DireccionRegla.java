package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public final class DireccionRegla implements Regla<Direccion> {
    private static final DireccionRegla INSTANCIA = new DireccionRegla();

    private DireccionRegla() {
        super();
    }

    public static DireccionRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_DIRECCION_NULO);
    }

    @Override
    public void validarCampos(Direccion modelo) {
        validarNombre(modelo.getNombre());
    }

    private void validarNombre(String nombre) {
        validarObligatorio(nombre, NOMBRE_DIRECCION_OBLIGATORIO);
        validarTextoValido(nombre, NOMBRE_DIRECCION_INVALIDO);
        validarNumeroEntre(nombre.length(), 10, 70, LONGITUD_NOMBRE_DIRECCION_INVALIDA);
    }
}