package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public final class AreaRegla implements Regla<Area> {
    private static final AreaRegla INSTANCIA = new AreaRegla();

    private AreaRegla() {
        super();
    }

    public static AreaRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_AREA_NULO);
    }

    @Override
    public void validarCampos(Area modelo) {
        validarNombre(modelo.getNombre());
    }

    private void validarNombre(String nombre) {
        validarObligatorio(nombre, NOMBRE_AREA_OBLIGATORIO);
        validarTextoValido(nombre, NOMBRE_AREA_INVALIDO);
        validarNumeroEntre(nombre.length(), 8, 70, LONGITUD_NOMBRE_AREA_INVALIDA);
    }
}