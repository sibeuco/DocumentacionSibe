package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoAlfanumericoValido;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public final class RelacionLaboralRegla implements Regla<RelacionLaboral> {
    private static final RelacionLaboralRegla INSTANCIA = new RelacionLaboralRegla();

    private RelacionLaboralRegla() {
        super();
    }

    public static RelacionLaboralRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_RELACION_LABORAL_NULO);
    }

    @Override
    public void validarCampos(RelacionLaboral modelo) {
        validarCodigo(modelo.getCodigo());
        validarDescripcion(modelo.getDescripcion());
    }

    private void validarCodigo(String codigo) {
        validarObligatorio(codigo, CODIGO_RELACION_OBLIGATORIO);
        validarTextoAlfanumericoValido(codigo, CODIGO_RELACION_INVALIDO);
        validarNumeroEntre(codigo.length(), 2, 4, LONGITUD_CODIGO_RELACION_INVALIDA);
    }

    private void validarDescripcion(String descripcion) {
        validarObligatorio(descripcion, DESCRIPCION_RELACION_LABORAL_OBLIGATORIA);
        validarTextoValido(descripcion, DESCRIPCION_RELACION_LABORAL_INVALIDA);
        validarNumeroEntre(descripcion.length(), 5, 20, LONGITUD_DESCRIPCION_RELACION_LABORAL_INVALIDA);
    }
}