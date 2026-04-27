package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.IDENTIFICADOR_REGISTRO_ASISTENCIA_NULO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_HAY_CAMPOS_POR_VALIDAR;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;

public class RegistroAsistenciaRegla implements Regla<RegistroAsistencia> {
    private static final RegistroAsistenciaRegla INSTANCIA = new RegistroAsistenciaRegla();

    private RegistroAsistenciaRegla() {
        super();
    }

    public static RegistroAsistenciaRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_REGISTRO_ASISTENCIA_NULO);
    }

    @Override
    public void validarCampos(RegistroAsistencia modelo) {
        throw new IllegalStateException(NO_HAY_CAMPOS_POR_VALIDAR);
    }
}