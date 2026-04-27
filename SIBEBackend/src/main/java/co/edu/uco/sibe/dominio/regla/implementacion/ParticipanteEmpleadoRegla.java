package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.ParticipanteEmpleado;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoAlfanumericoValido;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public class ParticipanteEmpleadoRegla implements Regla<ParticipanteEmpleado> {
    private static final ParticipanteEmpleadoRegla INSTANCIA = new ParticipanteEmpleadoRegla();

    private ParticipanteEmpleadoRegla() {
        super();
    }

    public static ParticipanteEmpleadoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_PARTICIPANTE_EMPLEADO_NULO);
    }

    @Override
    public void validarCampos(ParticipanteEmpleado modelo) {
        validarIdCarnet(modelo.getIdCarnet());
        validarSexo(modelo.getSexo());
    }

    private void validarIdCarnet(String idCarnet) {
        validarObligatorio(idCarnet, ID_CARNET_INTERNO_OBLIGATORIO);
        validarTextoAlfanumericoValido(idCarnet, ID_CARNET_INTERNO_INVALIDO);
        validarNumeroEntre(idCarnet.length(), 1, 20, LONGITUD_ID_CARNET_INTERNO_INVALIDA);
    }

    private void validarSexo(String sexo) {
        validarObligatorio(sexo, SEXO_PARTICIPANTE_INTERNO_OBLIGATORIO);
        validarTextoValido(sexo, SEXO_PARTICIPANTE_INTERNO_INVALIDO);
        validarNumeroEntre(sexo.length(), 1, 1, LONGITUD_SEXO_PARTICIPANTE_INTERNO_INVALIDA);
    }
}