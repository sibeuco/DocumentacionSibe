package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoAlfanumericoValido;

public final class CentroCostosRegla implements Regla<CentroCostos> {
    private static final CentroCostosRegla INSTANCIA = new CentroCostosRegla();

    private CentroCostosRegla() {
        super();
    }

    public static CentroCostosRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_CENTRO_COSTOS_NULO);
    }

    @Override
    public void validarCampos(CentroCostos modelo) {
        validarCodigo(modelo.getCodigo());
        validarDescripcion(modelo.getDescripcion());
    }

    private void validarCodigo(String codigo) {
        validarObligatorio(codigo, CODIGO_CENTRO_COSTOS_OBLIGATORIO);
        validarTextoAlfanumericoValido(codigo, CODIGO_CENTRO_COSTOS_INVALIDO);
        validarNumeroEntre(codigo.length(), 4, 6, LONGITUD_CODIGO_CENTRO_COSTOS_INVALIDA);
    }

    private void validarDescripcion(String descripcion) {
        validarObligatorio(descripcion, DESCRIPCION_CENTRO_COSTOS_OBLIGATORIA);
        validarTextoAlfanumericoValido(descripcion, DESCRIPCION_CENTRO_COSTOS_INVALIDA);
        validarNumeroEntre(descripcion.length(), 3, 100, LONGITUD_DESCRIPCION_CENTRO_COSTOS_INVALIDA);
    }
}