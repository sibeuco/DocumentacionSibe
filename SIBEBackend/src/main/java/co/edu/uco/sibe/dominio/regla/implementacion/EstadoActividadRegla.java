package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public final class EstadoActividadRegla implements Regla<EstadoActividad> {
    private static final EstadoActividadRegla INSTANCIA = new EstadoActividadRegla();

    private EstadoActividadRegla() {
        super();
    }

    public static EstadoActividadRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_ESTADO_ACTIVIDAD_NULO);
    }

    @Override
    public void validarCampos(EstadoActividad modelo) {
        validarNombre(modelo.getNombre());
    }

    private void validarNombre(String nombre) {
        validarObligatorio(nombre, NOMBRE_ESTADO_ACTIVIDAD_OBLIGATORIO);
        validarTextoValido(nombre, NOMBRE_ESTADO_ACTIVIDAD_INVALIDO);
        validarNumeroEntre(nombre.length(), 5, 15, LONGITUD_NOMBRE_ESTADO_ACTIVIDAD_INVALIDA);
    }
}