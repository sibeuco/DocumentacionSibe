package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.*;

public class EmpleadoRegla implements Regla<Empleado> {
    private static final EmpleadoRegla INSTANCIA = new EmpleadoRegla();

    private EmpleadoRegla() {
        super();
    }

    public static EmpleadoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_EMPLEADO_NULO);
    }

    @Override
    public void validarCampos(Empleado modelo) {
        validarNombreCompleto(modelo.getNombreCompleto());
        validarNumeroIdentificacion(modelo.getNumeroIdentificacion());
        validarIdCarnet(modelo.getIdCarnet());
        validarSexo(modelo.getSexo());
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

    private void validarIdCarnet(String idCarnet) {
        validarObligatorio(idCarnet, ID_CARNET_OBLIGATORIO);
        validarTextoAlfanumericoValido(idCarnet, ID_CARNET_INVALIDO);
        validarNumeroEntre(idCarnet.length(), 1, 20, LONGITUD_ID_CARNET_INVALIDA);
    }

    private void validarSexo(String sexo) {
        validarObligatorio(sexo, SEXO_OBLIGATORIO);
        validarTextoValido(sexo, SEXO_INVALIDO);
        validarNumeroEntre(sexo.length(), 1, 1, LONGITUD_SEXO_INVALIDA);
    }
}